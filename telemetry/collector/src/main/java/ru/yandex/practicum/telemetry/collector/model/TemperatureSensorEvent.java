package ru.yandex.practicum.telemetry.collector.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Событие датчика температуры, содержащее информацию о температуре в градусах Цельсия и Фаренгейта.
 */
@Getter
@Setter
@ToString(callSuper = true)
@Schema(description = "Событие датчика температуры, содержащее информацию о температуре в градусах Цельсия и Фаренгейта.")
public class TemperatureSensorEvent extends SensorEvent {

    /**
     * Температура в градусах Цельсия.
     */
    @Schema(description = "Температура в градусах Цельсия.")
    @NotNull
    private int temperatureC;

    /**
     * Температура в градусах Фаренгейта.
     */
    @Schema(description = "Температура в градусах Фаренгейта.")
    @NotNull
    private int temperatureF;

    /**
     * Возвращает тип события, всегда {@link SensorEventType#TEMPERATURE_SENSOR_EVENT}.
     *
     * @return Тип события датчика температуры.
     */
    @Override
    @Schema(description = "Тип события датчика температуры.")
    public SensorEventType getType() {
        return SensorEventType.TEMPERATURE_SENSOR_EVENT;
    }
}
