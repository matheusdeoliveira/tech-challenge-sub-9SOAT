package br.com.fiap.reseller_motors.infrastructure.persistence.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VehicleSpringRepository extends JpaRepository<VehicleJpa, UUID> {
    Page<VehicleJpa> findByStatusOrderByPriceAsc(String status, Pageable pageable);

    long countByStatus(String status);
}