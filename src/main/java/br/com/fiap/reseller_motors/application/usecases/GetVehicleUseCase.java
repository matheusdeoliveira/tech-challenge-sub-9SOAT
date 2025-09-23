package br.com.fiap.reseller_motors.application.usecases;

import br.com.fiap.reseller_motors.domain.model.Vehicle;
import br.com.fiap.reseller_motors.domain.ports.VehicleRepositoryPort;

import java.util.UUID;

public class GetVehicleUseCase {
    private final VehicleRepositoryPort repo;

    public GetVehicleUseCase(VehicleRepositoryPort repo) {
        this.repo = repo;
    }

    public Vehicle execute(UUID id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("VehicleNotFound"));
    }
}
