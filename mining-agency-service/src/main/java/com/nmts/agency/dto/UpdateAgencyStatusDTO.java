package com.nmts.agency.dto;

import com.nmts.agency.entity.OperationStatus;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;

public class UpdateAgencyStatusDTO {
    @NotNull(message = "Operation status is required")
    private OperationStatus operationStatus;

    public UpdateAgencyStatusDTO() {
    }

    public UpdateAgencyStatusDTO(OperationStatus operationStatus) {
        this.operationStatus = operationStatus;
    }

    public OperationStatus getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(OperationStatus operationStatus) {
        this.operationStatus = operationStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateAgencyStatusDTO that = (UpdateAgencyStatusDTO) o;
        return operationStatus == that.operationStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationStatus);
    }

    @Override
    public String toString() {
        return "UpdateAgencyStatusDTO{" +
                "operationStatus=" + operationStatus +
                '}';
    }
}
