package com.nmts.users.dto;

import java.util.Objects;
import java.util.UUID;

public class CreateUserInternalDTO {
    private UUID authUserId;
    private String name;
    private String email;
    private String role;

    public CreateUserInternalDTO() {}

    public CreateUserInternalDTO(UUID authUserId, String name, String email, String role) {
        this.authUserId = authUserId;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public static CreateUserInternalDTOBuilder builder() {
        return new CreateUserInternalDTOBuilder();
    }

    public UUID getAuthUserId() { return authUserId; }
    public void setAuthUserId(UUID authUserId) { this.authUserId = authUserId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateUserInternalDTO that = (CreateUserInternalDTO) o;
        return Objects.equals(authUserId, that.authUserId) && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authUserId, name, email, role);
    }

    @Override
    public String toString() {
        return "CreateUserInternalDTO{" +
                "authUserId=" + authUserId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public static class CreateUserInternalDTOBuilder {
        private UUID authUserId;
        private String name;
        private String email;
        private String role;

        CreateUserInternalDTOBuilder() {}

        public CreateUserInternalDTOBuilder authUserId(UUID authUserId) {
            this.authUserId = authUserId;
            return this;
        }

        public CreateUserInternalDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CreateUserInternalDTOBuilder email(String email) {
            this.email = email;
            return this;
        }

        public CreateUserInternalDTOBuilder role(String role) {
            this.role = role;
            return this;
        }

        public CreateUserInternalDTO build() {
            return new CreateUserInternalDTO(authUserId, name, email, role);
        }
    }
}
