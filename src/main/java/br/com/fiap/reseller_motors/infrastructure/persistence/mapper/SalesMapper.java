package br.com.fiap.reseller_motors.infrastructure.persistence.mapper;

import br.com.fiap.reseller_motors.domain.model.Sale;
import br.com.fiap.reseller_motors.infrastructure.persistence.jpa.SalesJpa;

public class SalesMapper {
    public static Sale toDomain(SalesJpa e) {
        if (e == null) return null;
        Sale d = new Sale(
                e.getId(),
                e.getVehicleId(),
                e.getBuyerCpf(),
                e.getPrice()
        );
        d.setCreatedAt(e.getCreatedAt());
        return d;
    }

    public static SalesJpa toEntity(Sale d) {
        if (d == null) return null;
        SalesJpa e = new SalesJpa();
        e.setId(d.getId());
        e.setVehicleId(d.getVehicleId());
        e.setBuyerCpf(d.getBuyerCpf());
        e.setPrice(d.getPrice());
        e.setCreatedAt(d.getCreatedAt());
        return e;
    }
}
