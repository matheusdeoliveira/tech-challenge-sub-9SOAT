package br.com.fiap.reseller_motors.application.usecases;

import br.com.fiap.reseller_motors.domain.model.Vehicle;
import br.com.fiap.reseller_motors.domain.ports.VehicleRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetVehicleUseCaseTest {

    private VehicleRepositoryPort vehicleRepo;
    private GetVehicleUseCase useCase;

    @BeforeEach
    void setup() {
        vehicleRepo = mock(VehicleRepositoryPort.class);
        useCase = new GetVehicleUseCase(vehicleRepo);
    }

    @Test
    void shouldGetVehicleById() {
        UUID id = UUID.randomUUID();
        Vehicle v = new Vehicle(id, "Fiat", "Argo", 2020, "Branco", new BigDecimal("55000"));
        when(vehicleRepo.findById(id)).thenReturn(Optional.of(v));
        Vehicle found = useCase.execute(id);
        assertEquals(id, found.getId());
        verify(vehicleRepo).findById(id);
    }

    @Test
    void shouldThrowWhenVehicleNotFound() {
        UUID id = UUID.randomUUID();
        when(vehicleRepo.findById(id)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> useCase.execute(id));
        verify(vehicleRepo).findById(id);
    }
}
