package br.com.fiap.reseller_motors.domain.ports;

import br.com.fiap.reseller_motors.domain.model.Vehicle;
import br.com.fiap.reseller_motors.domain.model.VehicleStatus;

import java.util.*;


public interface VehicleRepositoryPort {
    Vehicle save(Vehicle v);

    Optional<Vehicle> findById(UUID id);

    Vehicle update(Vehicle v);

    List<Vehicle> listByStatusOrderByPriceAsc(VehicleStatus status, int page, int pageSize);

    long countByStatus(VehicleStatus status);
}