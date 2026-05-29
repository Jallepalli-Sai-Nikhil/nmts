package com.nmts.users.dto;

import java.util.Objects;

public class UpdateProfileDTO {
    private String name;
    private String phone;
    private String address;
    private String businessName;

    public UpdateProfileDTO() {}

    public UpdateProfileDTO(String name, String phone, String address, String businessName) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.businessName = businessName;
    }

    public static UpdateProfileDTOBuilder builder() {
        return new UpdateProfileDTOBuilder();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getBusinessName() { return businessName; }
    public void setBusinessName(String businessName) { this.businessName = businessName; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateProfileDTO that = (UpdateProfileDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(phone, that.phone) && Objects.equals(address, that.address) && Objects.equals(businessName, that.businessName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone, address, businessName);
    }

    @Override
    public String toString() {
        return "UpdateProfileDTO{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", businessName='" + businessName + '\'' +
                '}';
    }

    public static class UpdateProfileDTOBuilder {
        private String name;
        private String phone;
        private String address;
        private String businessName;

        UpdateProfileDTOBuilder() {}

        public UpdateProfileDTOBuilder name(String name) { this.name = name; return this; }
        public UpdateProfileDTOBuilder phone(String phone) { this.phone = phone; return this; }
        public UpdateProfileDTOBuilder address(String address) { this.address = address; return this; }
        public UpdateProfileDTOBuilder businessName(String businessName) { this.businessName = businessName; return this; }

        public UpdateProfileDTO build() {
            return new UpdateProfileDTO(name, phone, address, businessName);
        }
    }
}
