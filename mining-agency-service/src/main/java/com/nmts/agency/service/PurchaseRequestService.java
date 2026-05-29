package com.nmts.agency.service;

import com.nmts.agency.client.LicenseServiceClient;
import com.nmts.agency.dto.LicenseStatusDTO;
import com.nmts.agency.dto.PurchaseRequestDTO;
import com.nmts.agency.dto.PurchaseResponseDTO;
import com.nmts.agency.dto.RejectRequestDTO;
import com.nmts.agency.entity.*;
import com.nmts.agency.exception.AccessDeniedException;
import com.nmts.agency.exception.BusinessRuleException;
import com.nmts.agency.exception.EntityNotFoundException;
import com.nmts.agency.kafka.event.PurchaseApprovedEvent;
import com.nmts.agency.kafka.event.PurchaseRejectedEvent;
import com.nmts.agency.kafka.event.PurchaseRequestedEvent;
import com.nmts.agency.kafka.producer.AgencyKafkaProducer;
import com.nmts.agency.repository.MetalListingRepository;
import com.nmts.agency.repository.MiningAgencyRepository;
import com.nmts.agency.repository.PurchaseRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PurchaseRequestService {

    private static final Logger log = LoggerFactory.getLogger(PurchaseRequestService.class);

    private final PurchaseRequestRepository requestRepository;
    private final MetalListingRepository listingRepository;
    private final MiningAgencyRepository agencyRepository;
    private final LicenseServiceClient licenseServiceClient;
    private final AgencyKafkaProducer kafkaProducer;

    public PurchaseRequestService(PurchaseRequestRepository requestRepository, MetalListingRepository listingRepository, MiningAgencyRepository agencyRepository, LicenseServiceClient licenseServiceClient, AgencyKafkaProducer kafkaProducer) {
        this.requestRepository = requestRepository;
        this.listingRepository = listingRepository;
        this.agencyRepository = agencyRepository;
        this.licenseServiceClient = licenseServiceClient;
        this.kafkaProducer = kafkaProducer;
    }

    @Transactional
    public PurchaseResponseDTO createRequest(UUID customerId, String customerName, PurchaseRequestDTO dto) {
        MetalListing listing = listingRepository.findById(dto.getListingId())
                .orElseThrow(() -> new EntityNotFoundException("Listing not found with ID: " + dto.getListingId()));

        if (!listing.getIsActive()) {
            throw new BusinessRuleException("Listing is no longer active");
        }

        MiningAgency agency = agencyRepository.findById(listing.getAgencyId())
                .orElseThrow(() -> new EntityNotFoundException("Agency not found for listing"));

        if (agency.getOperationStatus() != OperationStatus.ACTIVE) {
            throw new AccessDeniedException("Agency is not ACTIVE. Current status: " + agency.getOperationStatus());
        }

        LicenseStatusDTO licenseStatus = licenseServiceClient.getAgencyLicenseStatus(agency.getId());
        if (!licenseStatus.isHasActiveLicense()) {
            throw new AccessDeniedException("Agency must hold an active license to accept purchase requests");
        }

        BigDecimal totalValue = listing.getPricePerTon().multiply(BigDecimal.valueOf(dto.getRequestedQtyTons()));

        PurchaseRequest request = PurchaseRequest.builder()
                .id(UUID.randomUUID())
                .listingId(listing.getId())
                .agencyId(agency.getId())
                .customerId(customerId)
                .customerName(customerName)
                .requestedQtyTons(dto.getRequestedQtyTons())
                .pricePerTon(listing.getPricePerTon())
                .totalEstimatedValue(totalValue)
                .message(dto.getMessage())
                .status(PurchaseStatus.PENDING)
                .build();

        PurchaseRequest saved = requestRepository.save(request);

        kafkaProducer.publishPurchaseRequested(PurchaseRequestedEvent.builder()
                .requestId(saved.getId())
                .customerId(saved.getCustomerId())
                .customerName(saved.getCustomerName())
                .agencyId(saved.getAgencyId())
                .metalName(listing.getMetalName())
                .requestedQtyTons(saved.getRequestedQtyTons())
                .totalEstimatedValue(saved.getTotalEstimatedValue())
                .timestamp(LocalDateTime.now())
                .build());

        log.info("Purchase request created: {} for listing: {}", saved.getId(), listing.getId());
        return mapToResponse(saved, listing.getMetalName(), agency.getAgencyName());
    }

    public List<PurchaseResponseDTO> getMyRequests(UUID customerId) {
        return requestRepository.findByCustomerIdOrderByCreatedAtDesc(customerId)
                .stream()
                .map(this::mapToResponseWithNames)
                .toList();
    }

    public List<PurchaseResponseDTO> getIncomingRequests(UUID ownerId, PurchaseStatus status) {
        MiningAgency agency = agencyRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Agency not found for owner"));

        return requestRepository.findByAgencyIdAndStatus(agency.getId(), status)
                .stream()
                .map(this::mapToResponseWithNames)
                .toList();
    }

    public Page<PurchaseResponseDTO> getAllAgencyRequests(UUID ownerId, Pageable pageable) {
        MiningAgency agency = agencyRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Agency not found for owner"));

        return requestRepository.findByAgencyId(agency.getId(), pageable)
                .map(this::mapToResponseWithNames);
    }

    @Transactional
    public void approveRequest(UUID ownerId, UUID requestId) {
        MiningAgency agency = agencyRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Agency not found for owner"));

        PurchaseRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Purchase request not found with ID: " + requestId));

        if (!request.getAgencyId().equals(agency.getId())) {
            throw new AccessDeniedException("This request does not belong to your agency");
        }

        if (request.getStatus() != PurchaseStatus.PENDING) {
            throw new BusinessRuleException("Only PENDING requests can be approved. Current status: " + request.getStatus());
        }

        request.setStatus(PurchaseStatus.APPROVED);
        requestRepository.save(request);

        MetalListing listing = listingRepository.findById(request.getListingId())
                .orElseThrow(() -> new EntityNotFoundException("Listing not found"));

        kafkaProducer.publishPurchaseApproved(PurchaseApprovedEvent.builder()
                .requestId(request.getId())
                .customerId(request.getCustomerId())
                .agencyId(request.getAgencyId())
                .metalName(listing.getMetalName())
                .requestedQtyTons(request.getRequestedQtyTons())
                .totalEstimatedValue(request.getTotalEstimatedValue())
                .approvedAt(LocalDateTime.now())
                .build());

        log.info("Purchase request approved: {}", requestId);
    }

    @Transactional
    public void rejectRequest(UUID ownerId, UUID requestId, RejectRequestDTO dto) {
        MiningAgency agency = agencyRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Agency not found for owner"));

        PurchaseRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Purchase request not found with ID: " + requestId));

        if (!request.getAgencyId().equals(agency.getId())) {
            throw new AccessDeniedException("This request does not belong to your agency");
        }

        if (request.getStatus() != PurchaseStatus.PENDING) {
            throw new BusinessRuleException("Only PENDING requests can be rejected. Current status: " + request.getStatus());
        }

        request.setStatus(PurchaseStatus.REJECTED);
        request.setRejectionReason(dto.getRejectionReason());
        requestRepository.save(request);

        MetalListing listing = listingRepository.findById(request.getListingId())
                .orElseThrow(() -> new EntityNotFoundException("Listing not found"));

        kafkaProducer.publishPurchaseRejected(PurchaseRejectedEvent.builder()
                .requestId(request.getId())
                .customerId(request.getCustomerId())
                .agencyId(request.getAgencyId())
                .metalName(listing.getMetalName())
                .rejectionReason(request.getRejectionReason())
                .rejectedAt(LocalDateTime.now())
                .build());

        log.info("Purchase request rejected: {}", requestId);
    }

    public Page<PurchaseResponseDTO> getAllRequests(Pageable pageable) {
        return requestRepository.findAll(pageable).map(this::mapToResponseWithNames);
    }

    private PurchaseResponseDTO mapToResponse(PurchaseRequest request, String metalName, String agencyName) {
        return PurchaseResponseDTO.builder()
                .id(request.getId())
                .metalName(metalName)
                .agencyName(agencyName)
                .requestedQtyTons(request.getRequestedQtyTons())
                .pricePerTon(request.getPricePerTon())
                .totalEstimatedValue(request.getTotalEstimatedValue())
                .status(request.getStatus())
                .createdAt(request.getCreatedAt())
                .build();
    }

    private PurchaseResponseDTO mapToResponseWithNames(PurchaseRequest request) {
        String metalName = listingRepository.findById(request.getListingId())
                .map(MetalListing::getMetalName).orElse("Unknown");
        String agencyName = agencyRepository.findById(request.getAgencyId())
                .map(MiningAgency::getAgencyName).orElse("Unknown");
        return mapToResponse(request, metalName, agencyName);
    }
}
