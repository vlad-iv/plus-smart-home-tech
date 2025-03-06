package ru.yandex.practicum.telemetry.collector.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Перечисление типов устройств, которые могут быть добавлены в систему.
 */
@Schema(description = "Перечисление типов устройств, которые могут быть добавлены в систему.")
public enum DeviceType {

    /**
     * Датчик движения.
     */
    @Schema(description = "Датчик движения.")
    MOTION_SENSOR,

    /**
     * Датчик температуры.
     */
    @Schema(description = "Датчик температуры.")
    TEMPERATURE_SENSOR,

    /**
     * Датчик освещенности.
     */
    @Schema(description = "Датчик освещенности.")
    LIGHT_SENSOR,

    /**
     * Климатический датчик.
     */
    @Schema(description = "Климатический датчик.")
    CLIMATE_SENSOR,

    /**
     * Переключатель.
     */
    @Schema(description = "Переключатель.")
    SWITCH_SENSOR
}