package br.com.vitoria.ferreira.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("API Pagamento Simples")
                        .description("API para gerenciar transações e atualizar informações de pagamento")
                        .version("1.0.0"));


    }
}
