package br.com.fiap.reseller_motors.infrastructure.persistence.jpa;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "vehicle_history")
public class VehicleHistoryJpa {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID id;
    @Column(name = "vehicle_id")
    private UUID vehicleId;
    @Column(name = "change_type")
    private String changeType;
    @Column(name = "old_value_json", columnDefinition = "TEXT")
    private String oldValueJson;
    @Column(name = "new_value_json", columnDefinition = "TEXT")
    private String newValueJson;
    @Column(name = "occurred_at")
    private OffsetDateTime occurredAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(UUID vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getOldValueJson() {
        return oldValueJson;
    }

    public void setOldValueJson(String oldValueJson) {
        this.oldValueJson = oldValueJson;
    }

    public String getNewValueJson() {
        return newValueJson;
    }

    public void setNewValueJson(String newValueJson) {
        this.newValueJson = newValueJson;
    }

    public OffsetDateTime getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(OffsetDateTime occurredAt) {
        this.occurredAt = occurredAt;
    }
}
