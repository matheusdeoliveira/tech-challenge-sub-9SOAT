package br.com.fiap.reseller_motors.application.usecases;

import br.com.fiap.reseller_motors.domain.model.Vehicle;
import br.com.fiap.reseller_motors.domain.model.VehicleHistory;
import br.com.fiap.reseller_motors.domain.ports.VehicleHistoryRepositoryPort;
import br.com.fiap.reseller_motors.domain.ports.VehicleRepositoryPort;

import java.math.BigDecimal;
import java.util.UUID;

public class EditVehicleUseCase {
  private final VehicleRepositoryPort repo;
  private final VehicleHistoryRepositoryPort history;
  public EditVehicleUseCase(VehicleRepositoryPort repo, VehicleHistoryRepositoryPort history) { this.repo = repo; this.history = history; }
  public Vehicle execute(UUID id, BigDecimal newPrice, String newColor) {
    Vehicle v = repo.findById(id).orElseThrow(() -> new RuntimeException("VehicleNotFound"));
    String oldJson = String.format("{\"price\":%s,\"color\":\"%s\"}", v.getPrice(), v.getColor());
    v.edit(newPrice, newColor);
    Vehicle updated = repo.update(v);
    String newJson = String.format("{\"price\":%s,\"color\":\"%s\"}", updated.getPrice(), updated.getColor());
    history.append(new VehicleHistory(id, "EDITED", oldJson, newJson));
    return updated;
  }
}
