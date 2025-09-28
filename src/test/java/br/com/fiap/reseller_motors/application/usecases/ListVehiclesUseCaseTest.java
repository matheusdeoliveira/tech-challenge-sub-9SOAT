package br.com.fiap.reseller_motors.application.usecases;

import br.com.fiap.reseller_motors.domain.model.Vehicle;
import br.com.fiap.reseller_motors.domain.model.VehicleStatus;
import br.com.fiap.reseller_motors.domain.ports.VehicleRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListVehiclesUseCaseTest {

    private VehicleRepositoryPort vehicleRepo;
    private ListVehiclesUseCase useCase;

    @BeforeEach
    void setup() {
        vehicleRepo = mock(VehicleRepositoryPort.class);
        useCase = new ListVehiclesUseCase(vehicleRepo);
    }

    @Test
    void shouldListVehiclesAndReturnPaginationData() {
        int page = 2, pageSize = 3;
        List<Vehicle> items = List.of(
                new Vehicle(null, "Fiat", "Mobi", 2022, "Branco", new BigDecimal("45000")),
                new Vehicle(null, "VW", "Up", 2019, "Preto", new BigDecimal("40000"))
        );
        when(vehicleRepo.listByStatusOrderByPriceAsc(VehicleStatus.AVAILABLE, page, pageSize)).thenReturn(items);
        when(vehicleRepo.countByStatus(VehicleStatus.AVAILABLE)).thenReturn(10L);

        ListVehiclesUseCase.Result result = useCase.execute(VehicleStatus.AVAILABLE, page, pageSize);

        assertEquals(items, result.items);
        assertEquals(page, result.page);
        assertEquals(pageSize, result.pageSize);
        assertEquals(10L, result.total);

        verify(vehicleRepo).listByStatusOrderByPriceAsc(VehicleStatus.AVAILABLE, page, pageSize);
        verify(vehicleRepo).countByStatus(VehicleStatus.AVAILABLE);
        verifyNoMoreInteractions(vehicleRepo);
    }
}
