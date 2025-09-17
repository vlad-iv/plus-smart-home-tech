package ru.yandex.practicum.telemetry.collector.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.kafka.telemetry.event.DeviceTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.telemetry.collector.model.*;
import ru.yandex.practicum.telemetry.collector.service.handler.HubEventHandler;
import ru.yandex.practicum.telemetry.collector.service.handler.SensorEventHandler;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/events", consumes = MediaType.APPLICATION_JSON_VALUE)
public class EventController {
    private final Map<SensorEventType, SensorEventHandler> sensorEventHandlers;
    private final Map<HubEventType, HubEventHandler> hubEventHandlers;

    public EventController(Set<SensorEventHandler> sensorEventHandlers, Set<HubEventHandler> hubEventHandlers) {
        // Преобразовываем набор хендлеров в map, где ключ - тип события от конкретного датчика или hub'а
        // Это нужно для упрощения поиска подходящего хендлера во время обработки событий
        this.sensorEventHandlers = sensorEventHandlers.stream()
                .collect(Collectors.toMap(SensorEventHandler::getMessageType, Function.identity()));
        this.hubEventHandlers = hubEventHandlers.stream()
                .collect(Collectors.toMap(HubEventHandler::getMessageType, Function.identity()));
    }

    @PostMapping("/sensors")
    public void collectSensorEvent(@Valid @RequestBody SensorEvent request) {
        // Проверяем есть ли обработчик для полученного события
        SensorEventHandler sensorEventHandler = sensorEventHandlers.get(request.getType());
        if (sensorEventHandler == null) {
            throw new IllegalArgumentException("Не могу найти обработчик для события " + request.getType());
        }
        sensorEventHandler.handle(request);
    }

    @PostMapping("/hubs")
    public void collectHubEvent(@RequestBody @Valid HubEvent request) {
        log.info("json: {}", request.toString());
        if (request.getType() == HubEventType.DEVICE_ADDED) {
            DeviceAddedEvent deviceAddedEvent = (DeviceAddedEvent) request;
//            HubEventAvro.newBuilder().;
//            producer.send(avro);
        }
        HubEventHandler hubEventHandler = hubEventHandlers.get(request.getType());
        if (hubEventHandler == null) {
            throw new IllegalArgumentException("Не могу найти обработчик для события " + request.getType());
        }
        hubEventHandler.handle(request);
    }
}