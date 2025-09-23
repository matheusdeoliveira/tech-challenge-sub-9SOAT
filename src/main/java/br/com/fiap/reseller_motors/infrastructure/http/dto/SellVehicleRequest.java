package br.com.fiap.reseller_motors.infrastructure.http.dto;

import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

public class SellVehicleRequest {
    @Schema(example = "123.456.789-09")
    @NotBlank
    public String buyerCpf;
    @Schema(example = "2025-09-19T17:35:00-03:00")
    public String soldAt; // ISO-8601 opcional
}