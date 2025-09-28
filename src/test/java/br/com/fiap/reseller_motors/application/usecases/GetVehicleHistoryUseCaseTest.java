package br.com.fiap.reseller_motors.application.usecases;

import br.com.fiap.reseller_motors.domain.model.VehicleHistory;
import br.com.fiap.reseller_motors.domain.ports.VehicleHistoryRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetVehicleHistoryUseCaseTest {

    private VehicleHistoryRepositoryPort historyRepo;
    private GetVehicleHistoryUseCase useCase;

    @BeforeEach
    void setup() {
        historyRepo = mock(VehicleHistoryRepositoryPort.class);
        useCase = new GetVehicleHistoryUseCase(historyRepo);
    }

    @Test
    void shouldReturnHistoryList() {
        UUID id = UUID.randomUUID();
        VehicleHistory h1 = new VehicleHistory(id, "REGISTERED", null, "{}");
        VehicleHistory h2 = new VehicleHistory(id, "EDITED", "{}", "{}");
        when(historyRepo.findByVehicleId(id)).thenReturn(List.of(h1, h2));

        List<VehicleHistory> result = useCase.execute(id);
        assertEquals(2, result.size());
        assertEquals("REGISTERED", result.get(0).getChangeType());
        assertEquals("EDITED", result.get(1).getChangeType());

        verify(historyRepo).findByVehicleId(id);
        verifyNoMoreInteractions(historyRepo);
    }
}
