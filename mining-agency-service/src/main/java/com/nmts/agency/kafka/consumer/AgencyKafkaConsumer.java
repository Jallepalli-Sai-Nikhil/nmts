package com.nmts.agency.kafka.consumer;

import com.nmts.agency.dto.UpdateAgencyStatusDTO;
import com.nmts.agency.entity.OperationStatus;
import com.nmts.agency.kafka.event.OperationSeizedEvent;
import com.nmts.agency.service.AgencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AgencyKafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(AgencyKafkaConsumer.class);

    private final AgencyService agencyService;

    public AgencyKafkaConsumer(AgencyService agencyService) {
        this.agencyService = agencyService;
    }

    @KafkaListener(topics = "operation.seized", groupId = "nmts-group")
    public void consumeOperationSeized(OperationSeizedEvent event) {
        log.info("Consuming operation.seized event for agency: {}", event.getAgencyId());
        try {
            agencyService.updateAgencyStatus(event.getAgencyId(), new UpdateAgencyStatusDTO(OperationStatus.SEIZED));
            log.info("Agency {} status updated to SEIZED due to seizure order {}", event.getAgencyId(), event.getSeizureOrderId());
        } catch (Exception e) {
            log.error("Failed to update agency status for agency {}: {}", event.getAgencyId(), e.getMessage());
        }
    }
}
