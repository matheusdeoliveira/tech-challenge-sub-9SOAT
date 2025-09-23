package br.com.fiap.reseller_motors.infrastructure.http.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

public class EditVehicleRequest {
    @Schema(example = "52990.00")
    @DecimalMin(value = "0.0", inclusive = true)
    public BigDecimal price;
    @Schema(example = "Cinza")
    public String color;
}