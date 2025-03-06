package ru.yandex.practicum.telemetry.collector.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Событие климатического датчика, содержащее информацию о температуре, влажности и уровне CO2.
 */
@Getter
@Setter
@ToString(callSuper = true)
@Schema(description = "Событие климатического датчика, содержащее информацию о температуре, влажности и уровне CO2.")
@NotNull
public class ClimateSensorEvent extends SensorEvent {

    /**
     * Уровень температуры по шкале Цельсия.
     */
    @Schema(description = "Уровень температуры по шкале Цельсия.")
    @NotNull
    private int temperatureC;

    /**
     * Влажность.
     */
    @Schema(description = "Влажность.")
    @NotNull
    private int humidity;

    /**
     * Уровень CO2.
     */
    @Schema(description = "Уровень CO2.")
    @NotNull
    private int co2Level;

    /**
     * Возвращает тип события, всегда {@link SensorEventType#CLIMATE_SENSOR_EVENT}.
     *
     * @return Тип события климатического датчика.
     */
    @Override
    @Schema(description = "Тип события климатического датчика.")
    public SensorEventType getType() {
        return SensorEventType.CLIMATE_SENSOR_EVENT;
    }
}
