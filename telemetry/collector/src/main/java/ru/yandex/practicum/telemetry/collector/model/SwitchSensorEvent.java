package ru.yandex.practicum.telemetry.collector.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Событие датчика переключателя, содержащее информацию о текущем состоянии переключателя.
 */
@Getter
@Setter
@ToString(callSuper = true)
@Schema(description = "Событие датчика переключателя, содержащее информацию о текущем состоянии переключателя.")
public class SwitchSensorEvent extends SensorEvent {

    /**
     * Текущее состояние переключателя.
     * <p>true - включен, false - выключен.</p>
     */
    @Schema(description = "Текущее состояние переключателя. true - включен, false - выключен.")
    @NotNull
    private boolean state;

    /**
     * Возвращает тип события, всегда {@link SensorEventType#SWITCH_SENSOR_EVENT}.
     *
     * @return Тип события датчика переключателя.
     */
    @Override
    @Schema(description = "Тип события датчика переключателя.")
    public SensorEventType getType() {
        return SensorEventType.SWITCH_SENSOR_EVENT;
    }
}
