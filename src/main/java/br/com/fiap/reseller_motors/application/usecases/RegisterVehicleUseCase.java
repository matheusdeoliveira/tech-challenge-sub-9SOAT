package br.com.fiap.reseller_motors.application.usecases;

import br.com.fiap.reseller_motors.domain.model.Vehicle;
import br.com.fiap.reseller_motors.domain.model.VehicleHistory;
import br.com.fiap.reseller_motors.domain.model.VehicleStatus;
import br.com.fiap.reseller_motors.domain.ports.VehicleRepositoryPort;
import br.com.fiap.reseller_motors.domain.ports.VehicleHistoryRepositoryPort;

import java.math.BigDecimal;

public class RegisterVehicleUseCase {
    private final VehicleRepositoryPort repo;
    private final VehicleHistoryRepositoryPort history;

    public RegisterVehicleUseCase(VehicleRepositoryPort repo, VehicleHistoryRepositoryPort history) {
        this.repo = repo;
        this.history = history;
    }

    public Vehicle execute(String make, String model, Integer year, String color, BigDecimal price) {
        Vehicle v = new Vehicle(null, make, model, year, color, price);
        v.setStatus(VehicleStatus.AVAILABLE);
        Vehicle saved = repo.save(v);
        history.append(new VehicleHistory(saved.getId(), "REGISTERED", null, "{\"price\":" + price + "}"));
        return saved;
    }
}
