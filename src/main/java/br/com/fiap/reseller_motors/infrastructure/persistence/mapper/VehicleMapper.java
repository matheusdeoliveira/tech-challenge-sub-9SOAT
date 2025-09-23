package br.com.fiap.reseller_motors.infrastructure.persistence.mapper;

import br.com.fiap.reseller_motors.domain.model.Vehicle;
import br.com.fiap.reseller_motors.domain.model.VehicleStatus;
import br.com.fiap.reseller_motors.infrastructure.persistence.jpa.VehicleJpa;

import java.math.BigDecimal;

public class VehicleMapper {
    public static Vehicle toDomain(VehicleJpa e) {
        Vehicle v = new Vehicle(e.getId(), e.getMake(), e.getModel(), e.getYear(), e.getColor(), e.getPrice() == null ? BigDecimal.ZERO : e.getPrice());
        v.setVersion(e.getVersion());
        v.setStatus(VehicleStatus.valueOf(e.getStatus()));
        v.setCreatedAt(e.getCreatedAt());
        v.setUpdatedAt(e.getUpdatedAt());
        v.setSoldAt(e.getSoldAt());
        v.setSoldToCpf(e.getSoldToCpf());
        return v;
    }

    public static VehicleJpa toEntity(Vehicle v) {
        VehicleJpa e = new VehicleJpa();
        if (v.getId() != null) {
            e.setId(v.getId());
        }
        e.setVersion(v.getVersion());
        e.setMake(v.getMake());
        e.setModel(v.getModel());
        e.setYear(v.getYear());
        e.setColor(v.getColor());
        e.setPrice(v.getPrice());
        e.setStatus(v.getStatus().name());
        e.setCreatedAt(v.getCreatedAt());
        e.setUpdatedAt(v.getUpdatedAt());
        e.setSoldAt(v.getSoldAt());
        e.setSoldToCpf(v.getSoldToCpf());
        return e;
    }
}