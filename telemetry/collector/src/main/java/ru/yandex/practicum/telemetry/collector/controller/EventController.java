package ru.yandex.practicum.telemetry.collector.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import ru.yandex.practicum.telemetry.collector.model.SensorEvent;
import ru.yandex.practicum.telemetry.collector.model.SensorEventType;
import ru.yandex.practicum.telemetry.collector.service.handler.SensorEventHandler;

@Validated
@RestController
@RequestMapping(path = "/events", consumes = MediaType.APPLICATION_JSON_VALUE)
public class EventController {
	private final Map<SensorEventType, SensorEventHandler> sensorEventHandlers;

	public EventController(List<SensorEventHandler> sensorEventHandlers) {
		// Преобразовываем набор хендлеров в map, где ключ - тип события от конкретного датчика или hub'а
		// Это нужно для упрощения поиска подходящего хендлера во время обработки событий
		this.sensorEventHandlers = sensorEventHandlers.stream()
				.collect(Collectors.toMap(SensorEventHandler::getMessageType, Function.identity()));
	}

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
}
