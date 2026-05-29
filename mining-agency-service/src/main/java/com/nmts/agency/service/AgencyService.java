package com.nmts.agency.service;

import com.nmts.agency.dto.AgencyResponseDTO;
import com.nmts.agency.dto.CreateAgencyDTO;
import com.nmts.agency.dto.UpdateAgencyStatusDTO;
import com.nmts.agency.entity.MiningAgency;
import com.nmts.agency.entity.OperationStatus;
import com.nmts.agency.exception.DuplicateResourceException;
import com.nmts.agency.exception.EntityNotFoundException;
import com.nmts.agency.repository.MiningAgencyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AgencyService {

    private static final Logger log = LoggerFactory.getLogger(AgencyService.class);

    private final MiningAgencyRepository agencyRepository;
    private final com.nmts.agency.kafka.producer.AgencyKafkaProducer kafkaProducer;

    public AgencyService(MiningAgencyRepository agencyRepository, com.nmts.agency.kafka.producer.AgencyKafkaProducer kafkaProducer) {
        this.agencyRepository = agencyRepository;
        this.kafkaProducer = kafkaProducer;
    }

    @Transactional
    public AgencyResponseDTO registerAgency(UUID ownerId, CreateAgencyDTO dto) {
        if (agencyRepository.findByOwnerId(ownerId).isPresent()) {
            throw new DuplicateResourceException("Owner already has a registered agency");
        }

        MiningAgency agency = MiningAgency.builder()
                .id(UUID.randomUUID())
                .ownerId(ownerId)
                .agencyName(dto.getAgencyName())
                .agencyType(dto.getAgencyType())
                .registrationNumber(dto.getRegistrationNumber())
                .location(dto.getLocation())
                .description(dto.getDescription())
                .operationStatus(OperationStatus.ACTIVE)
                .build();

        MiningAgency saved = agencyRepository.save(agency);
        
        kafkaProducer.publishAgencyRegistered(com.nmts.agency.kafka.event.AgencyRegisteredEvent.builder()
                .agencyId(saved.getId())
                .ownerId(ownerId)
                .agencyName(saved.getAgencyName())
                .registrationNumber(saved.getRegistrationNumber())
                .timestamp(java.time.LocalDateTime.now())
                .build());

        log.info("Agency registered: {}", saved.getAgencyName());
        return mapToResponse(saved);
    }

    public AgencyResponseDTO getAgencyByOwner(UUID ownerId) {
        return agencyRepository.findByOwnerId(ownerId)
                .map(this::mapToResponse)
                .orElseThrow(() -> new EntityNotFoundException("Agency not found for owner: " + ownerId));
    }

    public AgencyResponseDTO getAgencyById(UUID id) {
        return agencyRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new EntityNotFoundException("Agency not found with ID: " + id));
    }

    public Page<AgencyResponseDTO> getAllAgencies(OperationStatus status, Pageable pageable) {
        if (status != null) {
            return agencyRepository.findByOperationStatus(status, pageable).map(this::mapToResponse);
        }
        return agencyRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Transactional
    public AgencyResponseDTO updateAgencyStatus(UUID id, UpdateAgencyStatusDTO dto) {
        MiningAgency agency = agencyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agency not found with ID: " + id));

        agency.setOperationStatus(dto.getOperationStatus());
        MiningAgency updated = agencyRepository.save(agency);
        log.info("Agency status updated: {} to {}", updated.getAgencyName(), updated.getOperationStatus());
        return mapToResponse(updated);
    }

    private AgencyResponseDTO mapToResponse(MiningAgency agency) {
        return AgencyResponseDTO.builder()
                .id(agency.getId())
                .agencyName(agency.getAgencyName())
                .agencyType(agency.getAgencyType())
                .registrationNumber(agency.getRegistrationNumber())
                .location(agency.getLocation())
                .operationStatus(agency.getOperationStatus())
                .createdAt(agency.getCreatedAt())
                .build();
    }
}
