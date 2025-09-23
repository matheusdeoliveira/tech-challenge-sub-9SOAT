package br.com.fiap.reseller_motors.infrastructure.persistence;

import br.com.fiap.reseller_motors.domain.model.Vehicle;
import br.com.fiap.reseller_motors.domain.model.VehicleStatus;
import br.com.fiap.reseller_motors.domain.ports.VehicleRepositoryPort;
import br.com.fiap.reseller_motors.infrastructure.persistence.jpa.VehicleJpa;
import br.com.fiap.reseller_motors.infrastructure.persistence.jpa.VehicleSpringRepository;
import br.com.fiap.reseller_motors.infrastructure.persistence.mapper.VehicleMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class VehicleRepositoryAdapter implements VehicleRepositoryPort {
    private final VehicleSpringRepository repo;

    public VehicleRepositoryAdapter(VehicleSpringRepository repo) {
        this.repo = repo;
    }

    public Vehicle save(Vehicle v) {
        return VehicleMapper.toDomain(repo.save(VehicleMapper.toEntity(v)));
    }

    public Optional<Vehicle> findById(UUID id) {
        return repo.findById(id).map(VehicleMapper::toDomain);
    }

    public Vehicle update(Vehicle v) {
        return VehicleMapper.toDomain(repo.save(VehicleMapper.toEntity(v)));
    }

    public List<Vehicle> listByStatusOrderByPriceAsc(VehicleStatus status, int page, int pageSize) {
        Page<VehicleJpa> p = repo.findByStatusOrderByPriceAsc(status.name(), PageRequest.of(page - 1, pageSize));
        return p.getContent().stream().map(VehicleMapper::toDomain).toList();
    }

    public long countByStatus(VehicleStatus status) {
        return repo.countByStatus(status.name());
    }
}