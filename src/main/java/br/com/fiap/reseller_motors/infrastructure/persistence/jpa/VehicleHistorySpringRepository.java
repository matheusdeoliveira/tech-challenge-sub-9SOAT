package br.com.fiap.reseller_motors.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VehicleHistorySpringRepository extends JpaRepository<VehicleHistoryJpa, UUID> {
    List<VehicleHistoryJpa> findByVehicleIdOrderByOccurredAtAsc(UUID vehicleId);
}