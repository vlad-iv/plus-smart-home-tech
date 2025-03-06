package ru.yandex.practicum.telemetry.collector.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Событие датчика освещенности, содержащее информацию о качестве связи и уровне освещенности.
 */
@Getter
@Setter
@ToString(callSuper = true)
@Schema(description = "Событие датчика освещенности, содержащее информацию о качестве связи и уровне освещенности.")
@NotNull
public class LightSensorEvent extends SensorEvent {

    /**
     * Качество связи.
     */
    @Schema(description = "Качество связи.")
    private int linkQuality;

    /**
     * Уровень освещенности.
     */
    @Schema(description = "Уровень освещенности.")
    private int luminosity;

    /**
     * Возвращает тип события, всегда {@link SensorEventType#LIGHT_SENSOR_EVENT}.
     *
     * @return Тип события датчика освещенности.
     */
    @Override
    @Schema(description = "Тип события датчика освещенности.")
    public SensorEventType getType() {
        return SensorEventType.LIGHT_SENSOR_EVENT;
    }
}