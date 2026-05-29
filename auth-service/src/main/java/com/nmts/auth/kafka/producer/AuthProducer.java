package com.nmts.auth.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.Objects;

@Service
public class AuthProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public AuthProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public static class UserRegisteredEvent {
        private UUID userId;
        private String email;
        private String name;
        private String role;

        public UserRegisteredEvent() {
        }

        public UserRegisteredEvent(UUID userId, String email, String name, String role) {
            this.userId = userId;
            this.email = email;
            this.name = name;
            this.role = role;
        }

        public UUID getUserId() {
            return userId;
        }

        public void setUserId(UUID userId) {
            this.userId = userId;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UserRegisteredEvent that = (UserRegisteredEvent) o;
            return Objects.equals(userId, that.userId) && Objects.equals(email, that.email) && Objects.equals(name, that.name) && Objects.equals(role, that.role);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, email, name, role);
        }

        @Override
        public String toString() {
            return "UserRegisteredEvent{" +
                    "userId=" + userId +
                    ", email='" + email + '\'' +
                    ", name='" + name + '\'' +
                    ", role='" + role + '\'' +
                    '}';
        }

        // Simple Builder
        public static UserRegisteredEventBuilder builder() {
            return new UserRegisteredEventBuilder();
        }

        public static class UserRegisteredEventBuilder {
            private UUID userId;
            private String email;
            private String name;
            private String role;

            public UserRegisteredEventBuilder userId(UUID userId) {
                this.userId = userId;
                return this;
            }

            public UserRegisteredEventBuilder email(String email) {
                this.email = email;
                return this;
            }

            public UserRegisteredEventBuilder name(String name) {
                this.name = name;
                return this;
            }

            public UserRegisteredEventBuilder role(String role) {
                this.role = role;
                return this;
            }

            public UserRegisteredEvent build() {
                return new UserRegisteredEvent(userId, email, name, role);
            }
        }
    }

    public void publishUserRegistered(UserRegisteredEvent event) {
        kafkaTemplate.send("user.registered", event.getUserId().toString(), event);
    }
}
