package br.com.fiap.reseller_motors.domain.model;

import br.com.fiap.reseller_motors.domain.vo.CPF;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class Sale {
    private UUID id;
    private UUID vehicleId;
    private String buyerCpf;
    private BigDecimal price;
    private OffsetDateTime createdAt;

    public Sale(UUID id, UUID vehicleId, String buyerCpf, BigDecimal price) {
        this.id = id;
        if (vehicleId == null) {
            throw new IllegalArgumentException("vehicleId is required");
        }
        this.vehicleId = vehicleId;
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Invalid price");
        }

        this.buyerCpf = new CPF(buyerCpf).getValue();
        this.price = price;
        this.createdAt = OffsetDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public UUID getVehicleId() {
        return vehicleId;
    }

    public String getBuyerCpf() {
        return buyerCpf;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setVehicleId(UUID vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setBuyerCpf(String buyerCpf) {
        this.buyerCpf = new CPF(buyerCpf).getValue();
    }

    public void setPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Invalid price");
        }
        this.price = price;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt == null ? OffsetDateTime.now() : createdAt;
    }
}
