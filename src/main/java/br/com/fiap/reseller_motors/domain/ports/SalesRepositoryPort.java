package br.com.fiap.reseller_motors.domain.ports;

import br.com.fiap.reseller_motors.domain.model.Sale;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SalesRepositoryPort {
    Sale save(Sale sale);
    Optional<Sale> findById(UUID id);
    List<Sale> findByVehicleId(UUID vehicleId);
}
