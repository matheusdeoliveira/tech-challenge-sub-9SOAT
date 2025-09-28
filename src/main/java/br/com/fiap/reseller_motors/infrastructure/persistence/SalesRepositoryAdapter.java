package br.com.fiap.reseller_motors.infrastructure.persistence;

import br.com.fiap.reseller_motors.domain.model.Sale;
import br.com.fiap.reseller_motors.domain.ports.SalesRepositoryPort;
import br.com.fiap.reseller_motors.infrastructure.persistence.jpa.SalesJpa;
import br.com.fiap.reseller_motors.infrastructure.persistence.jpa.SalesSpringRepository;
import br.com.fiap.reseller_motors.infrastructure.persistence.mapper.SalesMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class SalesRepositoryAdapter implements SalesRepositoryPort {
    private final SalesSpringRepository repo;

    public SalesRepositoryAdapter(SalesSpringRepository repo) {
        this.repo = repo;
    }

    @Override
    public Sale save(Sale sale) {
        SalesJpa saved = repo.save(SalesMapper.toEntity(sale));
        return SalesMapper.toDomain(saved);
    }

    @Override
    public Optional<Sale> findById(UUID id) {
        return repo.findById(id).map(SalesMapper::toDomain);
    }

    @Override
    public List<Sale> findByVehicleId(UUID vehicleId) {
        return repo.findByVehicleId(vehicleId).stream().map(SalesMapper::toDomain).toList();
    }
}
