package ru.yandex.practicum.telemetry.collector.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Событие, сигнализирующее о удалении устройства из системы.
 */
@Getter
@Setter
@ToString(callSuper = true)
@Schema(description = "Событие, сигнализирующее о удалении устройства из системы.")
@NotNull
public class DeviceRemovedEvent extends HubEvent {

    /**
     * Идентификатор удаленного устройства.
     */
    @Schema(description = "Идентификатор удаленного устройства.")
    @NotBlank
    private String id;

    /**
     * Возвращает тип события, всегда {@link HubEventType#DEVICE_REMOVED}.
     *
     * @return Тип события удаления устройства.
     */
    @Override
    @Schema(description = "Тип события удаления устройства.")
    public HubEventType getType() {
        return HubEventType.DEVICE_REMOVED;
    }
}