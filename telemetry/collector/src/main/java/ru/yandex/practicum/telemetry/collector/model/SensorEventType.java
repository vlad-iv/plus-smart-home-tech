package ru.yandex.practicum.telemetry.collector.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Перечисление типов событий датчиков.
 * Определяет различные типы событий, которые могут быть связаны с датчиками.
 */
@Schema(description = "Перечисление типов событий датчиков. Определяет различные типы событий, которые могут быть связаны с датчиками.")
public enum SensorEventType {

    /**
     * Событие от датчика движения.
     */
    @Schema(description = "Событие от датчика движения.")
    MOTION_SENSOR_EVENT,

    /**
     * Событие от датчика температуры.
     */
    @Schema(description = "Событие от датчика температуры.")
    TEMPERATURE_SENSOR_EVENT,

    /**
     * Событие от датчика освещенности.
     */
    @Schema(description = "Событие от датчика освещенности.")
    LIGHT_SENSOR_EVENT,

    /**
     * Событие от климатического датчика.
     */
    @Schema(description = "Событие от климатического датчика.")
    CLIMATE_SENSOR_EVENT,

    /**
     * Событие от переключателя.
     */
    @Schema(description = "Событие от переключателя.")
    SWITCH_SENSOR_EVENT
}