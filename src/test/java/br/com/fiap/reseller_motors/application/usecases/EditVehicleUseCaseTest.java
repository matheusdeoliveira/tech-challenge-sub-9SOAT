package br.com.fiap.reseller_motors.application.usecases;

import br.com.fiap.reseller_motors.domain.model.Vehicle;
import br.com.fiap.reseller_motors.domain.model.VehicleHistory;
import br.com.fiap.reseller_motors.domain.ports.VehicleHistoryRepositoryPort;
import br.com.fiap.reseller_motors.domain.ports.VehicleRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EditVehicleUseCaseTest {

    private VehicleRepositoryPort vehicleRepo;
    private VehicleHistoryRepositoryPort historyRepo;
    private EditVehicleUseCase useCase;

    @BeforeEach
    void setup() {
        vehicleRepo = mock(VehicleRepositoryPort.class);
        historyRepo = mock(VehicleHistoryRepositoryPort.class);
        useCase = new EditVehicleUseCase(vehicleRepo, historyRepo);
    }

    @Test
    void shouldEditVehicleAndAppendHistory() {
        // arrange
        UUID id = UUID.randomUUID();
        Vehicle existing = new Vehicle(id, "VW", "Nivus", 2022, "Preto", new BigDecimal("100000"));
        when(vehicleRepo.findById(id)).thenReturn(Optional.of(existing));
        when(vehicleRepo.update(any(Vehicle.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(historyRepo.append(any(VehicleHistory.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // act
        Vehicle updated = useCase.execute(id, new BigDecimal("95000"), "Branco");

        // assert
        assertEquals(new BigDecimal("95000"), updated.getPrice());
        assertEquals("Branco", updated.getColor());

        ArgumentCaptor<VehicleHistory> histCap = ArgumentCaptor.forClass(VehicleHistory.class);
        verify(historyRepo).append(histCap.capture());
        VehicleHistory hist = histCap.getValue();
        assertEquals(id, hist.getVehicleId());
        assertEquals("EDITED", hist.getChangeType());
        assertNotNull(hist.getOldValueJson());
        assertTrue(hist.getOldValueJson().contains("\"price\":100000"));
        assertTrue(hist.getNewValueJson().contains("\"price\":95000"));
        assertTrue(hist.getNewValueJson().contains("\"color\":\"Branco\""));

        verify(vehicleRepo).findById(id);
        verify(vehicleRepo).update(any(Vehicle.class));
        verifyNoMoreInteractions(vehicleRepo);
    }

    @Test
    void shouldThrowWhenVehicleNotFound() {
        UUID id = UUID.randomUUID();
        when(vehicleRepo.findById(id)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> useCase.execute(id, new BigDecimal("1"), "Azul"));
        verify(vehicleRepo).findById(id);
        verify(vehicleRepo, never()).update(any());
        verifyNoInteractions(historyRepo);
    }
}
