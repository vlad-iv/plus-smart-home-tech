package ru.yandex.practicum.telemetry.collector.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Событие, сигнализирующее о добавлении нового устройства в систему.
 */
@Getter
@Setter
@ToString(callSuper = true)
@Schema(description = "Событие, сигнализирующее о добавлении нового устройства в систему.")
@NotNull
public class DeviceAddedEvent extends HubEvent {

    /**
     * Идентификатор добавленного устройства.
     */
    @Schema(description = "Идентификатор добавленного устройства.")
    @NotBlank
    private String id;

    /**
     * Тип добавленного устройства.
     */
    @Schema(description = "Тип добавленного устройства.")
    @NotNull
    private DeviceType deviceType;

    /**
     * Возвращает тип события, всегда {@link HubEventType#DEVICE_ADDED}.
     *
     * @return Тип события добавления устройства.
     */
    @Override
    @Schema(description = "Тип события добавления устройства.")
    public HubEventType getType() {
        return HubEventType.DEVICE_ADDED;
    }
}
