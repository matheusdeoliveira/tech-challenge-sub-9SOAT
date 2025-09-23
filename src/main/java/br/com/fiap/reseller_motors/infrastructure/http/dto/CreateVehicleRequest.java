package br.com.fiap.reseller_motors.infrastructure.http.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

public class CreateVehicleRequest {
    @Schema(example = "Fiat")
    @NotBlank
    public String make;
    @Schema(example = "Argo")
    @NotBlank
    public String model;
    @Schema(example = "2022")
    @NotNull
    @Min(1950)
    @Max(2100)
    public Integer year;
    @Schema(example = "Branco")
    @NotBlank
    public String color;
    @Schema(example = "55990.00")
    @NotNull
    @DecimalMin("0.0")
    public BigDecimal price;
}