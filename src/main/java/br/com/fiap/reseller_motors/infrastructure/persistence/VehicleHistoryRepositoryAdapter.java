package br.com.fiap.reseller_motors.infrastructure.persistence;

import br.com.fiap.reseller_motors.domain.model.VehicleHistory;
import br.com.fiap.reseller_motors.domain.ports.VehicleHistoryRepositoryPort;
import br.com.fiap.reseller_motors.infrastructure.persistence.jpa.VehicleHistorySpringRepository;
import br.com.fiap.reseller_motors.infrastructure.persistence.mapper.VehicleHistoryMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class VehicleHistoryRepositoryAdapter implements VehicleHistoryRepositoryPort {
    private final VehicleHistorySpringRepository repo;

    public VehicleHistoryRepositoryAdapter(VehicleHistorySpringRepository repo) {
        this.repo = repo;
    }

    public VehicleHistory append(VehicleHistory h) {
        return VehicleHistoryMapper.toDomain(repo.save(VehicleHistoryMapper.toEntity(h)));
    }

    public List<VehicleHistory> findByVehicleId(UUID vehicleId) {
        return repo.findByVehicleIdOrderByOccurredAtAsc(vehicleId).stream().map(VehicleHistoryMapper::toDomain).toList();
    }
}