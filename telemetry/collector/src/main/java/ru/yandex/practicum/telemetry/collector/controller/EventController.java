package ru.yandex.practicum.telemetry.collector.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.telemetry.collector.model.*;
import ru.yandex.practicum.telemetry.collector.service.handler.HubEventHandler;
import ru.yandex.practicum.telemetry.collector.service.handler.SensorEventHandler;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping(path = "/events", consumes = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "events", description = "API для передачи событий от датчиков и хабов")
public class EventController {
    private final Map<SensorEventType, SensorEventHandler> sensorEventHandlers;
    private final Map<HubEventType, HubEventHandler> hubEventHandlers;

    /**
     * Конструктор класса.
     *
     * @param sensorEventHandlers Обработчики событий от датчиков. Это классы реализующие SensorEventHandler и являющиеся spring bean
     * @param hubEventHandlers    Обработчики событий от хабов. Это классы реализующие HubEventHandler и являющиеся spring bean
     */
    public EventController(Set<SensorEventHandler> sensorEventHandlers, Set<HubEventHandler> hubEventHandlers) {
        // Преобразовываем набор хендлеров в map, где ключ - тип события от конкретного датчика или hub'а
        // Это нужно для упрощения поиска подходящего хендлера во время обработки событий
        this.sensorEventHandlers = sensorEventHandlers.stream()
                .collect(Collectors.toMap(SensorEventHandler::getMessageType, Function.identity()));
        this.hubEventHandlers = hubEventHandlers.stream()
                .collect(Collectors.toMap(HubEventHandler::getMessageType, Function.identity()));
    }

    /**
     * Метод для обработки событий от датчиков.
     *
     * @param request Событие от датчика
     */
    @Operation(description = "Эндпоинт для обработки событий от датчиков", summary = "Обработчик событий датчиков")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Данные события датчика (показания, изменение состояния и т.д)",
            content = @Content(
                    schema = @Schema(oneOf = {
                            ClimateSensorEvent.class, LightSensorEvent.class,
                            MotionSensorEvent.class, SwitchSensorEvent.class,
                            TemperatureSensorEvent.class
                    }),
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "LightSensorEvent",
                                    description = "Показания датчика освещённости",
                                    value = """
                                            {
                                                "id": "sensor.light.3",
                                                "hubId": "hub-2",
                                                "timestamp": "2024-08-06T16:54:03.129Z",
                                                "type": "LIGHT_SENSOR_EVENT",
                                                "linkQuality": 75,
                                                "luminosity": 59
                                            }
                                            """
                            ),
                            @ExampleObject(
                                    name = "SwitchSensorEvent",
                                    description = "Событие изменения состояния переключателя",
                                    value = """
                                            {
                                                "id": "sensor.switch.4356",
                                                "hubId": "hub-1",
                                                "timestamp": "2024-08-06T17:54:03.129Z",
                                                "type": "SWITCH_SENSOR_EVENT",
                                                "state": true
                                            }
                                            """
                            )
                    }
            )
    )
    @PostMapping("/sensors")
    public void collectSensorEvent(@Valid @RequestBody SensorEvent request) {
        // Проверяем есть ли обработчик для полученного события
        if (sensorEventHandlers.containsKey(request.getType())) {
            // если обработчик найден - передаём событие ему на обработку
            sensorEventHandlers.get(request.getType()).handle(request);
        } else {
            throw new IllegalArgumentException("Не могу найти обработчик для события " + request.getType());
        }
    }

    /**
     * Метод для обработки событий от хаба.
     *
     * @param request Событие от хаба
     */
    @PostMapping("/hubs")
    @Operation(description = "Эндпоинт для обработки событий от хаба", summary = "Обработчик событий хабов")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Данные события хаба (регистрация/удаление устройств в хабе, добавление/удаление сценария умного дома)",
            content = @Content(
                    schema = @Schema(oneOf = { DeviceAddedEvent.class, DeviceRemovedEvent.class, ScenarioAddedEvent.class, ScenarioRemovedEvent.class }),
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "DeviceAddedEvent",
                                    description = "Событие регистрации нового датчика освещенности в хабе пользователя",
                                    value = """
                                            {
                                              "hubId": "hub.12345",
                                              "timestamp": "2024-08-06T15:11:24.157Z",
                                              "type": "DEVICE_ADDED",
                                              "id": "sensor.light.3",
                                              "deviceType": "MOTION_SENSOR"
                                            }
                                            """
                            )
                    }
            )
    )
    public void collectHubEvent(@RequestBody @Valid HubEvent request) {
        // Проверяем есть ли обработчик для полученного события
        if (hubEventHandlers.containsKey(request.getType())) {
            // если обработчик найден - передаём событие ему на обработку
            hubEventHandlers.get(request.getType()).handle(request);
        } else {
            throw new IllegalArgumentException("Не могу найти обработчик для события " + request.getType());
        }
    }
}