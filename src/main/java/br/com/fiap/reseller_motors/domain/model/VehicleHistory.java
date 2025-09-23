package br.com.fiap.reseller_motors.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public class VehicleHistory {
    private UUID id;
    private UUID vehicleId;
    private String changeType;
    private String oldValueJson;
    private String newValueJson;
    private OffsetDateTime occurredAt;

    public VehicleHistory(UUID vehicleId, String changeType, String oldValueJson, String newValueJson) {
        this.vehicleId = vehicleId;
        this.changeType = changeType;
        this.oldValueJson = oldValueJson;
        this.newValueJson = newValueJson;
        this.occurredAt = OffsetDateTime.now();
    }

    public VehicleHistory() {
    }

    public UUID getId() {
        return id;
    }

    public UUID getVehicleId() {
        return vehicleId;
    }

    public String getChangeType() {
        return changeType;
    }

    public String getOldValueJson() {
        return oldValueJson;
    }

    public String getNewValueJson() {
        return newValueJson;
    }

    public OffsetDateTime getOccurredAt() {
        return occurredAt;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setVehicleId(UUID vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public void setOldValueJson(String oldValueJson) {
        this.oldValueJson = oldValueJson;
    }

    public void setNewValueJson(String newValueJson) {
        this.newValueJson = newValueJson;
    }

    public void setOccurredAt(OffsetDateTime occurredAt) {
        this.occurredAt = occurredAt;
    }
}
