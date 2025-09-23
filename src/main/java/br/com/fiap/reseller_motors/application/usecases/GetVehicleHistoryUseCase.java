package br.com.fiap.reseller_motors.application.usecases;

import br.com.fiap.reseller_motors.domain.model.VehicleHistory;
import br.com.fiap.reseller_motors.domain.ports.VehicleHistoryRepositoryPort;

import java.util.*;


public class GetVehicleHistoryUseCase {
    private final VehicleHistoryRepositoryPort repo;

    public GetVehicleHistoryUseCase(VehicleHistoryRepositoryPort repo) {
        this.repo = repo;
    }

    public List<VehicleHistory> execute(UUID vehicleId) {
        return repo.findByVehicleId(vehicleId);
    }
}
