package br.com.fiap.reseller_motors.infrastructure.config;

import br.com.fiap.reseller_motors.application.usecases.*;
import br.com.fiap.reseller_motors.infrastructure.persistence.VehicleHistoryRepositoryAdapter;
import br.com.fiap.reseller_motors.infrastructure.persistence.VehicleRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfig {
    @Bean
    RegisterVehicleUseCase registerVehicleUseCase(VehicleRepositoryAdapter repo, VehicleHistoryRepositoryAdapter hist) {
        return new RegisterVehicleUseCase(repo, hist);
    }

    @Bean
    EditVehicleUseCase editVehicleUseCase(VehicleRepositoryAdapter repo, VehicleHistoryRepositoryAdapter hist) {
        return new EditVehicleUseCase(repo, hist);
    }

    @Bean
    SellVehicleUseCase sellVehicleUseCase(VehicleRepositoryAdapter repo, VehicleHistoryRepositoryAdapter hist) {
        return new SellVehicleUseCase(repo, hist);
    }

    @Bean
    ListVehiclesUseCase listVehiclesUseCase(VehicleRepositoryAdapter repo) {
        return new ListVehiclesUseCase(repo);
    }

    @Bean
    GetVehicleUseCase getVehicleUseCase(VehicleRepositoryAdapter repo) {
        return new GetVehicleUseCase(repo);
    }

    @Bean
    GetVehicleHistoryUseCase getVehicleHistoryUseCase(VehicleHistoryRepositoryAdapter hist) {
        return new GetVehicleHistoryUseCase(hist);
    }
}