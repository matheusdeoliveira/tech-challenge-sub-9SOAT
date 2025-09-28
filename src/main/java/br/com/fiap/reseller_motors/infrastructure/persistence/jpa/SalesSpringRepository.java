package br.com.fiap.reseller_motors.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SalesSpringRepository extends JpaRepository<SalesJpa, UUID> {
    List<SalesJpa> findByVehicleId(UUID vehicleId);
}
