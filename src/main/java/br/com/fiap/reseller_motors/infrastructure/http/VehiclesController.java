package br.com.fiap.reseller_motors.infrastructure.http;

import br.com.fiap.reseller_motors.application.usecases.*;
import br.com.fiap.reseller_motors.domain.model.Vehicle;
import br.com.fiap.reseller_motors.domain.model.VehicleHistory;
import br.com.fiap.reseller_motors.domain.model.VehicleStatus;
import br.com.fiap.reseller_motors.infrastructure.http.dto.CreateVehicleRequest;
import br.com.fiap.reseller_motors.infrastructure.http.dto.EditVehicleRequest;
import br.com.fiap.reseller_motors.infrastructure.http.dto.PagedVehiclesResponse;
import br.com.fiap.reseller_motors.infrastructure.http.dto.SellVehicleRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Tag(name = "Vehicles", description = "Operações de cadastro, edição, venda e consulta de veículos")
@RestController
@RequestMapping("/api/vehicles")
public class VehiclesController {

    private final RegisterVehicleUseCase registerUC;
    private final EditVehicleUseCase editUC;
    private final SellVehicleUseCase sellUC;
    private final ListVehiclesUseCase listUC;
    private final GetVehicleUseCase getUC;
    private final GetVehicleHistoryUseCase historyUC;

    public VehiclesController(RegisterVehicleUseCase registerUC, EditVehicleUseCase editUC, SellVehicleUseCase sellUC,
                              ListVehiclesUseCase listUC, GetVehicleUseCase getUC, GetVehicleHistoryUseCase historyUC) {
        this.registerUC = registerUC;
        this.editUC = editUC;
        this.sellUC = sellUC;
        this.listUC = listUC;
        this.getUC = getUC;
        this.historyUC = historyUC;
    }

    @Operation(summary = "Cadastrar veículo", description = "Cria um novo veículo disponível para venda")
    @ApiResponse(responseCode = "200", description = "Veículo cadastrado", content = @Content(schema = @Schema(implementation = Vehicle.class)))
    @PostMapping
    public Vehicle create(@Valid @RequestBody CreateVehicleRequest req) {
        return registerUC.execute(req.make, req.model, req.year, req.color, req.price);
    }

    @Operation(summary = "Editar veículo", description = "Atualiza preço e/ou cor do veículo")
    @PatchMapping("/{id}")
    public Vehicle edit(@Parameter(description = "ID do veículo") @PathVariable UUID id, @Valid @RequestBody EditVehicleRequest req) {
        return editUC.execute(id, req.price, req.color);
    }

    @Operation(summary = "Efetuar venda", description = "Marca um veículo como vendido com CPF e data (opcional)")
    @PostMapping("/{id}/sell")
    public Vehicle sell(@Parameter(description = "ID do veículo") @PathVariable UUID id, @Valid @RequestBody SellVehicleRequest req) {
        OffsetDateTime when = null;
        if (req.soldAt != null && !req.soldAt.isBlank()) {
            when = OffsetDateTime.parse(req.soldAt);
        }
        return sellUC.execute(id, req.buyerCpf, when);
    }

    @Operation(summary = "Listar veículos", description = "Lista veículos por status (available/sold) ordenados por preço asc")
    @GetMapping
    public PagedVehiclesResponse list(
            @Parameter(example = "available") @RequestParam(defaultValue = "available") String status,
            @Parameter(example = "1") @RequestParam(defaultValue = "1") int page,
            @Parameter(example = "20") @RequestParam(defaultValue = "20") int pageSize) {
        VehicleStatus st = "sold".equalsIgnoreCase(status) ? VehicleStatus.SOLD : VehicleStatus.AVAILABLE;
        ListVehiclesUseCase.Result r = listUC.execute(st, page, pageSize);
        return new PagedVehiclesResponse(r.items, r.page, r.pageSize, r.total);
    }

    @Operation(summary = "Detalhar veículo")
    @GetMapping("/{id}")
    public Vehicle get(@Parameter(description = "ID do veículo") @PathVariable UUID id) {
        return getUC.execute(id);
    }

    @Operation(summary = "Histórico de alterações do veículo")
    @GetMapping("/{id}/history")
    public List<VehicleHistory> history(@Parameter(description = "ID do veículo") @PathVariable UUID id) {
        return historyUC.execute(id);
    }
}
