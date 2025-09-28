package br.com.fiap.reseller_motors.application.usecases;

import br.com.fiap.reseller_motors.domain.model.Vehicle;
import br.com.fiap.reseller_motors.domain.model.VehicleHistory;
import br.com.fiap.reseller_motors.domain.ports.VehicleHistoryRepositoryPort;
import br.com.fiap.reseller_motors.domain.ports.VehicleRepositoryPort;
import br.com.fiap.reseller_motors.domain.ports.SalesRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SellVehicleUseCaseTest {

    private VehicleRepositoryPort vehicleRepo;
    private VehicleHistoryRepositoryPort historyRepo;
    private SalesRepositoryPort salesRepo;
    private SellVehicleUseCase useCase;

    @BeforeEach
    void setup() {
        vehicleRepo = mock(VehicleRepositoryPort.class);
        historyRepo = mock(VehicleHistoryRepositoryPort.class);
        salesRepo = mock(SalesRepositoryPort.class);
        when(salesRepo.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        useCase = new SellVehicleUseCase(vehicleRepo, historyRepo, salesRepo);
    }

    @Test
    void shouldSellVehicleUpdatePriceAndAppendHistory() {
        UUID id = UUID.randomUUID();
        Vehicle existing = new Vehicle(id, "Chevrolet", "Onix", 2021, "Azul", new BigDecimal("70000"));
        when(vehicleRepo.findById(id)).thenReturn(Optional.of(existing));
        when(vehicleRepo.update(any(Vehicle.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(historyRepo.append(any(VehicleHistory.class))).thenAnswer(invocation -> invocation.getArgument(0));

        OffsetDateTime soldAt = OffsetDateTime.parse("2024-12-31T12:00:00Z");
        Vehicle updated = useCase.execute(id, "123.456.789-09", new BigDecimal("69000"), soldAt);

        assertEquals(new BigDecimal("69000"), updated.getPrice());
        assertEquals("12345678909", updated.getSoldToCpf());
        assertEquals(soldAt, updated.getSoldAt());
        assertNotNull(updated.getUpdatedAt());

        ArgumentCaptor<VehicleHistory> histCap = ArgumentCaptor.forClass(VehicleHistory.class);
        verify(historyRepo).append(histCap.capture());
        VehicleHistory hist = histCap.getValue();
        assertEquals(id, hist.getVehicleId());
        assertEquals("SOLD", hist.getChangeType());
        assertNull(hist.getOldValueJson());
        assertTrue(hist.getNewValueJson().contains("\"soldToCpf\":\"12345678909\""));
        assertTrue(hist.getNewValueJson().contains("\"soldAt\":\"" + soldAt + "\""));
        assertTrue(hist.getNewValueJson().contains("\"price\":69000"));

        verify(vehicleRepo).findById(id);
        verify(vehicleRepo).update(any(Vehicle.class));
    }

    @Test
    void shouldThrowIfAlreadySold() {
        UUID id = UUID.randomUUID();
        Vehicle v = new Vehicle(id, "VW", "T-Cross", 2020, "Preto", new BigDecimal("80000"));
        // First sale
        v.sell("12345678909", OffsetDateTime.now());
        when(vehicleRepo.findById(id)).thenReturn(Optional.of(v));
        assertThrows(IllegalStateException.class, () -> useCase.execute(id, "98765432100", null, null));
        verify(vehicleRepo).findById(id);
        verify(vehicleRepo, never()).update(any());
        verifyNoInteractions(historyRepo);
    }

    @Test
    void shouldThrowWhenVehicleNotFound() {
        UUID id = UUID.randomUUID();
        when(vehicleRepo.findById(id)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> useCase.execute(id, "12345678909", null, null));
        verify(vehicleRepo).findById(id);
        verifyNoInteractions(historyRepo);
    }
}
