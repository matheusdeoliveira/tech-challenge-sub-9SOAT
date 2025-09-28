package br.com.fiap.reseller_motors.application.usecases;

import br.com.fiap.reseller_motors.domain.model.Sale;
import br.com.fiap.reseller_motors.domain.model.Vehicle;
import br.com.fiap.reseller_motors.domain.model.VehicleHistory;
import br.com.fiap.reseller_motors.domain.ports.SalesRepositoryPort;
import br.com.fiap.reseller_motors.domain.ports.VehicleHistoryRepositoryPort;
import br.com.fiap.reseller_motors.domain.ports.VehicleRepositoryPort;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class SellVehicleUseCase {
    private final VehicleRepositoryPort repo;
    private final VehicleHistoryRepositoryPort history;
    private final SalesRepositoryPort salesRepo;

    public SellVehicleUseCase(VehicleRepositoryPort repo, VehicleHistoryRepositoryPort history, SalesRepositoryPort salesRepo) {
        this.repo = repo;
        this.history = history;
        this.salesRepo = salesRepo;
    }

    public Vehicle execute(UUID id, String buyerCpf, BigDecimal price, OffsetDateTime soldAt) {
        Vehicle v = repo.findById(id).orElseThrow(() -> new RuntimeException("VehicleNotFound"));
        if (price != null) {
            v.edit(price, null);
        }
        v.sell(buyerCpf, soldAt);
        Vehicle updated = repo.update(v);

        Sale sale = new Sale(null, id, buyerCpf, updated.getPrice());
        salesRepo.save(sale);

        String newJson = String.format("{\"soldToCpf\":\"%s\",\"soldAt\":\"%s\",\"price\":%s}",
                updated.getSoldToCpf(), updated.getSoldAt(), updated.getPrice());
        history.append(new VehicleHistory(id, "SOLD", null, newJson));
        return updated;
    }
}
