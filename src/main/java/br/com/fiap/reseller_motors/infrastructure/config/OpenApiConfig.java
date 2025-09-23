package br.com.fiap.reseller_motors.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Autos API",
                version = "v1",
                description = "API de revenda de veículos com DDD + Arquitetura Hexagonal",
                contact = @Contact(name = "Equipe Autos"),
                license = @License(name = "MIT")
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Local")
        }
)
@Configuration
public class OpenApiConfig {
}
