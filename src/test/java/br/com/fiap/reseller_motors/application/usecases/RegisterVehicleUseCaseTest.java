package br.com.fiap.reseller_motors.application.usecases;

import br.com.fiap.reseller_motors.domain.model.Vehicle;
import br.com.fiap.reseller_motors.domain.model.VehicleHistory;
import br.com.fiap.reseller_motors.domain.model.VehicleStatus;
import br.com.fiap.reseller_motors.domain.ports.VehicleHistoryRepositoryPort;
import br.com.fiap.reseller_motors.domain.ports.VehicleRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RegisterVehicleUseCaseTest {

    private VehicleRepositoryPort vehicleRepo;
    private VehicleHistoryRepositoryPort historyRepo;
    private RegisterVehicleUseCase useCase;

    @BeforeEach
    void setup() {
        vehicleRepo = mock(VehicleRepositoryPort.class);
        historyRepo = mock(VehicleHistoryRepositoryPort.class);
        useCase = new RegisterVehicleUseCase(vehicleRepo, historyRepo);
    }

    @Test
    void shouldRegisterVehicleAndAppendHistory() {
        // arrange
        when(vehicleRepo.save(any(Vehicle.class))).thenAnswer(invocation -> {
            Vehicle v = invocation.getArgument(0);
            v.setId(UUID.randomUUID());
            return v;
        });
        when(historyRepo.append(any(VehicleHistory.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // act
        BigDecimal price = new BigDecimal("123456.78");
        Vehicle saved = useCase.execute("Fiat", "Pulse", 2023, "Prata", price);

        // assert
        assertNotNull(saved.getId());
        assertEquals(VehicleStatus.AVAILABLE, saved.getStatus());
        assertEquals(price, saved.getPrice());
        assertEquals("Prata", saved.getColor());

        ArgumentCaptor<VehicleHistory> histCap = ArgumentCaptor.forClass(VehicleHistory.class);
        verify(historyRepo, times(1)).append(histCap.capture());
        VehicleHistory history = histCap.getValue();
        assertEquals(saved.getId(), history.getVehicleId());
        assertEquals("REGISTERED", history.getChangeType());
        assertNull(history.getOldValueJson());
        assertTrue(history.getNewValueJson().contains("\"price\":" + price));

        verify(vehicleRepo, times(1)).save(any(Vehicle.class));
        verifyNoMoreInteractions(vehicleRepo);
    }
}
