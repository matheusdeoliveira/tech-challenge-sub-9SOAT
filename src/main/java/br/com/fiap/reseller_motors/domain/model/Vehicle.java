package br.com.fiap.reseller_motors.domain.model;

import br.com.fiap.reseller_motors.domain.vo.CPF;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

public class Vehicle {
    private UUID id;
    private Long version;
    private String make;
    private String model;
    private Integer year;
    private String color;
    private BigDecimal price;
    private VehicleStatus status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private OffsetDateTime soldAt;
    private String soldToCpf;

    public Vehicle(UUID id, String make, String model, Integer year, String color, BigDecimal price) {
        this.id = id;
        this.make = Objects.requireNonNull(make);
        this.model = Objects.requireNonNull(model);
        this.year = Objects.requireNonNull(year);
        this.color = Objects.requireNonNull(color);
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Invalid price");
        this.price = price;
        this.status = VehicleStatus.AVAILABLE;
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = this.createdAt;
    }

    public void edit(BigDecimal newPrice, String newColor) {
        if (newPrice != null) {
            if (newPrice.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Invalid price");
            this.price = newPrice;
        }
        if (newColor != null && !newColor.isBlank()) this.color = newColor;
        this.updatedAt = OffsetDateTime.now();
    }

    public void sell(String buyerCpf, OffsetDateTime when) {
        if (this.status == VehicleStatus.SOLD) throw new IllegalStateException("Vehicle already sold");
        new CPF(buyerCpf);
        this.status = VehicleStatus.SOLD;
        this.soldToCpf = buyerCpf.replaceAll("\\D", "");
        this.soldAt = when == null ? OffsetDateTime.now() : when;
        this.updatedAt = this.soldAt;
    }

    public boolean isAvailable() {
        return this.status == VehicleStatus.AVAILABLE;
    }

    public UUID getId() {
        return id;
    }

    public Long getVersion() {
        return version;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public Integer getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public OffsetDateTime getSoldAt() {
        return soldAt;
    }

    public String getSoldToCpf() {
        return soldToCpf;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setStatus(VehicleStatus status) {
        this.status = status;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setSoldAt(OffsetDateTime soldAt) {
        this.soldAt = soldAt;
    }

    public void setSoldToCpf(String soldToCpf) {
        this.soldToCpf = soldToCpf;
    }
}
