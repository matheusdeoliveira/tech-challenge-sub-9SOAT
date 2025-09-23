package br.com.fiap.reseller_motors.domain.ports;

import br.com.fiap.reseller_motors.domain.model.VehicleHistory;

import java.util.List;
import java.util.UUID;

public interface VehicleHistoryRepositoryPort {
    VehicleHistory append(VehicleHistory h);

    List<VehicleHistory> findByVehicleId(UUID vehicleId);
}