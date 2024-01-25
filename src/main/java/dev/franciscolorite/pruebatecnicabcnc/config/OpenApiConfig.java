package dev.franciscolorite.pruebatecnicabcnc.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Francisco José Lorite Fernández",
                        email = "pacolori@gmail.com"
                ),
                description = "Documentación generada con OpenApi para la prueba técnica de la compañía BCNC",
                title = "API de la aplicación BCNC - Lorite",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                )
        }
)
public class OpenApiConfig {
}
