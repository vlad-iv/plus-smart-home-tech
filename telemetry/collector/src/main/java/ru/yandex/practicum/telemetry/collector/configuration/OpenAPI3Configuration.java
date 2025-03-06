package ru.yandex.practicum.telemetry.collector.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/*
* Данный класс только для генерации спецификации API!
* Студентам в своих работах его реализовывать не нужно!
* */
@Configuration
public class OpenAPI3Configuration {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Smart Home Technologies API")
                        .version("1.0")
                )
                .addServersItem(new Server()
                        .description("Сервер для локальной разработки")
                        .url("http://localhost:8080")
                );
    }
}