package br.com.fiap.reseller_motors.infrastructure.http.dto;

import br.com.fiap.reseller_motors.domain.model.Vehicle;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public class PagedVehiclesResponse {
    @Schema(description = "Itens retornados")
    public final List<Vehicle> items;
    public final int page;
    public final int pageSize;
    public final long total;

    public PagedVehiclesResponse(List<Vehicle> items, int page, int pageSize, long total) {
        this.items = items;
        this.page = page;
        this.pageSize = pageSize;
        this.total = total;
    }
}