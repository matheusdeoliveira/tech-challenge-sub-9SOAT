package br.com.fiap.reseller_motors.infrastructure.seed;


import br.com.fiap.reseller_motors.application.usecases.RegisterVehicleUseCase;
import br.com.fiap.reseller_motors.application.usecases.SellVehicleUseCase;
import br.com.fiap.reseller_motors.domain.model.Vehicle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Component
public class SeedRunner implements CommandLineRunner {
    private final boolean seed;
    private final boolean seedReset;
    private final RegisterVehicleUseCase registerUC;
    private final SellVehicleUseCase sellUC;

    public SeedRunner(@Value("${app.seed:false}") boolean seed, @Value("${app.seedReset:false}") boolean seedReset,
                      RegisterVehicleUseCase registerUC, SellVehicleUseCase sellUC) {
        this.seed = seed;
        this.seedReset = seedReset;
        this.registerUC = registerUC;
        this.sellUC = sellUC;
    }

    public void run(String... args) {
        if (!seed) return;
        Vehicle v1 = registerUC.execute("Fiat", "Argo", 2022, "Branco", new BigDecimal("55990.00"));
        Vehicle v2 = registerUC.execute("Volkswagen", "Gol", 2020, "Prata", new BigDecimal("44990.00"));
        Vehicle v3 = registerUC.execute("Chevrolet", "Onix", 2023, "Preto", new BigDecimal("79990.00"));
        Vehicle v4 = registerUC.execute("Hyundai", "HB20", 2021, "Azul", new BigDecimal("65990.00"));
        Vehicle v5 = registerUC.execute("Toyota", "Corolla", 2019, "Cinza", new BigDecimal("92990.00"));
        Vehicle v6 = registerUC.execute("Honda", "Civic", 2018, "Preto", new BigDecimal("89990.00"));
        Vehicle v7 = registerUC.execute("Ford", "Ka", 2019, "Vermelho", new BigDecimal("39990.00"));
        Vehicle v8 = registerUC.execute("Renault", "Sandero", 2020, "Branco", new BigDecimal("42990.00"));
        Vehicle v9 = registerUC.execute("Peugeot", "208", 2021, "Azul", new BigDecimal("61990.00"));
        Vehicle v10 = registerUC.execute("Nissan", "Kicks", 2022, "Prata", new BigDecimal("109990.00"));

        // Marcar alguns como vendidos
        sellUC.execute(v4.getId(), "123.456.789-09", new BigDecimal("39990.00"), OffsetDateTime.now().minusDays(2));
        sellUC.execute(v5.getId(), "111.222.333-96", new BigDecimal("39990.00"), OffsetDateTime.now().minusDays(1));
        sellUC.execute(v10.getId(), "987.654.321-00", new BigDecimal("39990.00"), OffsetDateTime.now());
    }
}
