package br.com.fiap.reseller_motors.application.usecases;

import br.com.fiap.reseller_motors.domain.model.Vehicle;
import br.com.fiap.reseller_motors.domain.model.VehicleStatus;
import br.com.fiap.reseller_motors.domain.ports.VehicleRepositoryPort;

import java.util.List;

public class ListVehiclesUseCase {
    private final VehicleRepositoryPort repo;

    public ListVehiclesUseCase(VehicleRepositoryPort repo) {
        this.repo = repo;
    }

    public Result execute(VehicleStatus status, int page, int pageSize) {
        List<Vehicle> items = repo.listByStatusOrderByPriceAsc(status, page, pageSize);
        long total = repo.countByStatus(status);
        return new Result(items, page, pageSize, total);
    }

    public static class Result {
        public final List<Vehicle> items;
        public final int page;
        public final int pageSize;
        public final long total;

        public Result(List<Vehicle> items, int page, int pageSize, long total) {
            this.items = items;
            this.page = page;
            this.pageSize = pageSize;
            this.total = total;
        }
    }
}
