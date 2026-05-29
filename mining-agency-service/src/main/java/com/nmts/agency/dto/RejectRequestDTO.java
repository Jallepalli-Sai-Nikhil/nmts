package com.nmts.agency.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

public class RejectRequestDTO {
    @NotBlank(message = "Rejection reason is required")
    private String rejectionReason;

    public RejectRequestDTO() {
    }

    public RejectRequestDTO(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RejectRequestDTO that = (RejectRequestDTO) o;
        return Objects.equals(rejectionReason, that.rejectionReason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rejectionReason);
    }

    @Override
    public String toString() {
        return "RejectRequestDTO{" +
                "rejectionReason='" + rejectionReason + '\'' +
                '}';
    }
}
