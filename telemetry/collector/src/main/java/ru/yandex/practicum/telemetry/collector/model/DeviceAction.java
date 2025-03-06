package ru.yandex.practicum.telemetry.collector.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.Data;

/**
 * Представляет действие, которое должно быть выполнено устройством.
 */
@Data
@Schema(description = "Представляет действие, которое должно быть выполнено устройством.")
public class DeviceAction {

    /**
     * Идентификатор датчика, связанного с действием.
     */
    @Schema(description = "Идентификатор датчика, связанного с действием.")
    private String sensorId;

    /**
     * Тип действия, которое должно быть выполнено.
     */
    @Schema(description = "Тип действия, которое должно быть выполнено.")
    private ActionType type;

    /**
     * Необязательное значение, связанное с действием.
     */
    @Schema(description = "Необязательное значение, связанное с действием.")
    private @Nullable Integer value;
}