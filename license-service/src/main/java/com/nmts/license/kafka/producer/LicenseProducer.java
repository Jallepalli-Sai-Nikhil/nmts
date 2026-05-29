package com.nmts.license.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
public class LicenseProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public LicenseProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public static class LicenseGrantedEvent {
        private UUID licenseId;
        private UUID agencyId;
        private String agencyName;
        private String licenseType;
        private LocalDateTime expiresAt;
        private LocalDateTime timestamp;

        public LicenseGrantedEvent() {
        }

        public LicenseGrantedEvent(UUID licenseId, UUID agencyId, String agencyName, String licenseType, LocalDateTime expiresAt, LocalDateTime timestamp) {
            this.licenseId = licenseId;
            this.agencyId = agencyId;
            this.agencyName = agencyName;
            this.licenseType = licenseType;
            this.expiresAt = expiresAt;
            this.timestamp = timestamp;
        }

        public UUID getLicenseId() {
            return licenseId;
        }

        public void setLicenseId(UUID licenseId) {
            this.licenseId = licenseId;
        }

        public UUID getAgencyId() {
            return agencyId;
        }

        public void setAgencyId(UUID agencyId) {
            this.agencyId = agencyId;
        }

        public String getAgencyName() {
            return agencyName;
        }

        public void setAgencyName(String agencyName) {
            this.agencyName = agencyName;
        }

        public String getLicenseType() {
            return licenseType;
        }

        public void setLicenseType(String licenseType) {
            this.licenseType = licenseType;
        }

        public LocalDateTime getExpiresAt() {
            return expiresAt;
        }

        public void setExpiresAt(LocalDateTime expiresAt) {
            this.expiresAt = expiresAt;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LicenseGrantedEvent that = (LicenseGrantedEvent) o;
            return Objects.equals(licenseId, that.licenseId) && Objects.equals(agencyId, that.agencyId) && Objects.equals(agencyName, that.agencyName) && Objects.equals(licenseType, that.licenseType) && Objects.equals(expiresAt, that.expiresAt) && Objects.equals(timestamp, that.timestamp);
        }

        @Override
        public int hashCode() {
            return Objects.hash(licenseId, agencyId, agencyName, licenseType, expiresAt, timestamp);
        }

        @Override
        public String toString() {
            return "LicenseGrantedEvent{" +
                    "licenseId=" + licenseId +
                    ", agencyId=" + agencyId +
                    ", agencyName='" + agencyName + '\'' +
                    ", licenseType='" + licenseType + '\'' +
                    ", expiresAt=" + expiresAt +
                    ", timestamp=" + timestamp +
                    '}';
        }

        public static LicenseGrantedEventBuilder builder() {
            return new LicenseGrantedEventBuilder();
        }

        public static class LicenseGrantedEventBuilder {
            private UUID licenseId;
            private UUID agencyId;
            private String agencyName;
            private String licenseType;
            private LocalDateTime expiresAt;
            private LocalDateTime timestamp;

            public LicenseGrantedEventBuilder licenseId(UUID licenseId) {
                this.licenseId = licenseId;
                return this;
            }

            public LicenseGrantedEventBuilder agencyId(UUID agencyId) {
                this.agencyId = agencyId;
                return this;
            }

            public LicenseGrantedEventBuilder agencyName(String agencyName) {
                this.agencyName = agencyName;
                return this;
            }

            public LicenseGrantedEventBuilder licenseType(String licenseType) {
                this.licenseType = licenseType;
                return this;
            }

            public LicenseGrantedEventBuilder expiresAt(LocalDateTime expiresAt) {
                this.expiresAt = expiresAt;
                return this;
            }

            public LicenseGrantedEventBuilder timestamp(LocalDateTime timestamp) {
                this.timestamp = timestamp;
                return this;
            }

            public LicenseGrantedEvent build() {
                return new LicenseGrantedEvent(licenseId, agencyId, agencyName, licenseType, expiresAt, timestamp);
            }
        }
    }

    public void publishLicenseGranted(LicenseGrantedEvent event) {
        kafkaTemplate.send("license.granted", event.getAgencyId().toString(), event);
    }

    public void publishLicenseRevoked(UUID agencyId) {
        kafkaTemplate.send("license.revoked", agencyId.toString(), agencyId.toString());
    }
}
