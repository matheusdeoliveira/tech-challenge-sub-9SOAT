package br.com.fiap.reseller_motors.infrastructure.persistence.mapper;

import br.com.fiap.reseller_motors.domain.model.VehicleHistory;
import br.com.fiap.reseller_motors.infrastructure.persistence.jpa.VehicleHistoryJpa;

public class VehicleHistoryMapper {
    public static VehicleHistory toDomain(VehicleHistoryJpa e) {
        VehicleHistory h = new VehicleHistory();
        h.setId(e.getId());
        h.setVehicleId(e.getVehicleId());
        h.setChangeType(e.getChangeType());
        h.setOldValueJson(e.getOldValueJson());
        h.setNewValueJson(e.getNewValueJson());
        h.setOccurredAt(e.getOccurredAt());
        return h;
    }

    public static VehicleHistoryJpa toEntity(VehicleHistory h) {
        VehicleHistoryJpa e = new VehicleHistoryJpa();
        e.setId(h.getId());
        e.setVehicleId(h.getVehicleId());
        e.setChangeType(h.getChangeType());
        e.setOldValueJson(h.getOldValueJson());
        e.setNewValueJson(h.getNewValueJson());
        e.setOccurredAt(h.getOccurredAt());
        return e;
    }
}