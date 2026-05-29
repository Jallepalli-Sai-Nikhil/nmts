package com.nmts.agency.service;

import com.nmts.agency.client.LicenseServiceClient;
import com.nmts.agency.dto.CreateListingDTO;
import com.nmts.agency.dto.LicenseStatusDTO;
import com.nmts.agency.dto.ListingResponseDTO;
import com.nmts.agency.dto.UpdateListingDTO;
import com.nmts.agency.entity.MetalListing;
import com.nmts.agency.entity.MiningAgency;
import com.nmts.agency.entity.OperationStatus;
import com.nmts.agency.exception.AccessDeniedException;
import com.nmts.agency.exception.EntityNotFoundException;
import com.nmts.agency.kafka.event.ListingCreatedEvent;
import com.nmts.agency.kafka.producer.AgencyKafkaProducer;
import com.nmts.agency.repository.MetalListingRepository;
import com.nmts.agency.repository.MiningAgencyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ListingService {

    private static final Logger log = LoggerFactory.getLogger(ListingService.class);

    private final MetalListingRepository listingRepository;
    private final MiningAgencyRepository agencyRepository;
    private final LicenseServiceClient licenseServiceClient;
    private final AgencyKafkaProducer kafkaProducer;

    public ListingService(MetalListingRepository listingRepository, MiningAgencyRepository agencyRepository, LicenseServiceClient licenseServiceClient, AgencyKafkaProducer kafkaProducer) {
        this.listingRepository = listingRepository;
        this.agencyRepository = agencyRepository;
        this.licenseServiceClient = licenseServiceClient;
        this.kafkaProducer = kafkaProducer;
    }

    @Transactional
    public ListingResponseDTO createListing(UUID ownerId, CreateListingDTO dto) {
        MiningAgency agency = agencyRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Agency not found for owner: " + ownerId));

        if (agency.getOperationStatus() != OperationStatus.ACTIVE) {
            throw new AccessDeniedException("Agency is not ACTIVE. Current status: " + agency.getOperationStatus());
        }

        LicenseStatusDTO licenseStatus = licenseServiceClient.getAgencyLicenseStatus(agency.getId());
        if (!licenseStatus.isHasActiveLicense()) {
            throw new AccessDeniedException("Agency must hold an active license to create listings");
        }

        MetalListing listing = MetalListing.builder()
                .id(UUID.randomUUID())
                .agencyId(agency.getId())
                .metalName(dto.getMetalName())
                .metalCategory(dto.getMetalCategory())
                .description(dto.getDescription())
                .pricePerTon(dto.getPricePerTon())
                .availableQtyTons(dto.getAvailableQtyTons())
                .isActive(true)
                .build();

        MetalListing saved = listingRepository.save(listing);

        kafkaProducer.publishListingCreated(ListingCreatedEvent.builder()
                .listingId(saved.getId())
                .agencyId(saved.getAgencyId())
                .agencyName(agency.getAgencyName())
                .metalName(saved.getMetalName())
                .pricePerTon(saved.getPricePerTon())
                .availableQtyTons(saved.getAvailableQtyTons())
                .timestamp(LocalDateTime.now())
                .build());

        log.info("Listing created: {} by agency: {}", saved.getMetalName(), agency.getAgencyName());
        return mapToResponse(saved, agency.getAgencyName());
    }

    public Page<ListingResponseDTO> getActiveListings(String metalName, Pageable pageable) {
        return listingRepository.findActiveListingsFromActiveAgencies(metalName, pageable)
                .map(listing -> {
                    String agencyName = agencyRepository.findById(listing.getAgencyId())
                            .map(MiningAgency::getAgencyName).orElse("Unknown");
                    return mapToResponse(listing, agencyName);
                });
    }

    public List<ListingResponseDTO> searchListings(String metalName) {
        return listingRepository.findActiveListingsByExactMetalNameFromActiveAgencies(metalName)
                .stream()
                .map(listing -> {
                    String agencyName = agencyRepository.findById(listing.getAgencyId())
                            .map(MiningAgency::getAgencyName).orElse("Unknown");
                    return mapToResponse(listing, agencyName);
                }).toList();
    }

    public ListingResponseDTO getListingById(UUID id) {
        MetalListing listing = listingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Listing not found with ID: " + id));
        String agencyName = agencyRepository.findById(listing.getAgencyId())
                .map(MiningAgency::getAgencyName).orElse("Unknown");
        return mapToResponse(listing, agencyName);
    }

    public List<ListingResponseDTO> getMyListings(UUID ownerId) {
        MiningAgency agency = agencyRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Agency not found for owner: " + ownerId));

        return listingRepository.findByAgencyIdAndIsActiveTrue(agency.getId())
                .stream()
                .map(listing -> mapToResponse(listing, agency.getAgencyName()))
                .toList();
    }

    @Transactional
    public ListingResponseDTO updateListing(UUID ownerId, UUID listingId, UpdateListingDTO dto) {
        MiningAgency agency = agencyRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Agency not found for owner: " + ownerId));

        MetalListing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new EntityNotFoundException("Listing not found with ID: " + listingId));

        if (!listing.getAgencyId().equals(agency.getId())) {
            throw new AccessDeniedException("You do not own this listing");
        }

        if (dto.getDescription() != null) listing.setDescription(dto.getDescription());
        if (dto.getPricePerTon() != null) listing.setPricePerTon(dto.getPricePerTon());
        if (dto.getAvailableQtyTons() != null) listing.setAvailableQtyTons(dto.getAvailableQtyTons());
        if (dto.getIsActive() != null) listing.setIsActive(dto.getIsActive());

        MetalListing updated = listingRepository.save(listing);
        log.info("Listing updated: {}", updated.getId());
        return mapToResponse(updated, agency.getAgencyName());
    }

    @Transactional
    public void deleteListing(UUID ownerId, UUID listingId) {
        MiningAgency agency = agencyRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Agency not found for owner: " + ownerId));

        MetalListing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new EntityNotFoundException("Listing not found with ID: " + listingId));

        if (!listing.getAgencyId().equals(agency.getId())) {
            throw new AccessDeniedException("You do not own this listing");
        }

        listing.setIsActive(false);
        listingRepository.save(listing);
        log.info("Listing soft deleted: {}", listingId);
    }

    private ListingResponseDTO mapToResponse(MetalListing listing, String agencyName) {
        return ListingResponseDTO.builder()
                .id(listing.getId())
                .agencyId(listing.getAgencyId())
                .agencyName(agencyName)
                .metalName(listing.getMetalName())
                .metalCategory(listing.getMetalCategory())
                .description(listing.getDescription())
                .pricePerTon(listing.getPricePerTon())
                .availableQtyTons(listing.getAvailableQtyTons())
                .unit(listing.getUnit())
                .isActive(listing.getIsActive())
                .createdAt(listing.getCreatedAt())
                .build();
    }
}
