package ru.yandex.practicum.telemetry.collector.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Событие датчика движения.
 */
@Getter  @Setter @ToString(callSuper = true)
@Schema(description = "Событие датчика движения.")
public class MotionSensorEvent extends SensorEvent {

    /**
     * Качество связи.
     */
    @Schema(description = "Качество связи.")
    @NotNull
    private int linkQuality;

    /**
     * Состояние занятости.
     */
    @Schema(description = "Наличие/отсутствие движения.")
    @NotNull
    private boolean motion;

    /**
     * Напряжение.
     */
    @Schema(description = "Напряжение.")
    @NotNull
    private int voltage;

    /**
     * Возвращает тип события, всегда {@link SensorEventType#MOTION_SENSOR_EVENT}.
     *
     * @return Тип события датчика движения.
     */
    @Override
    @Schema(description = "Тип события датчика движения.")
    public SensorEventType getType() {
        return SensorEventType.MOTION_SENSOR_EVENT;
    }
}