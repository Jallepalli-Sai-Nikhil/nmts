package com.nmts.users.kafka.consumer;

import com.nmts.users.dto.CreateUserInternalDTO;
import com.nmts.users.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class UserRegistrationConsumer {

    private static final Logger log = LoggerFactory.getLogger(UserRegistrationConsumer.class);

    private final UserService userService;

    public UserRegistrationConsumer(UserService userService) {
        this.userService = userService;
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
    }

    @KafkaListener(topics = "user.registered", groupId = "user-service-group")
    public void consumeUserRegistered(UserRegisteredEvent event) {
        log.info("Creating profile for newly registered user: {}", event.getEmail());
        CreateUserInternalDTO dto = CreateUserInternalDTO.builder()
                .authUserId(event.getUserId())
                .email(event.getEmail())
                .name(event.getName())
                .role(event.getRole())
                .build();
        userService.createUserProfile(dto);
    }
}
