package ru.yandex.practicum.telemetry.collector.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Событие удаления сценария из системы.
 * Содержит информацию о названии удаленного сценария.
 */
@Getter
@Setter
@ToString(callSuper = true)
@Schema(description = "Событие удаления сценария из системы. Содержит информацию о названии удаленного сценария.")
public class ScenarioRemovedEvent extends HubEvent {

    /**
     * Название удаленного сценария.
     * Должно содержать не менее 3 символов.
     */
    @Schema(description = "Название удаленного сценария. Должно содержать не менее 3 символов.")
    @NotEmpty
    @Size(min = 3)
    private String name;

    /**
     * Возвращает тип события, всегда {@link HubEventType#SCENARIO_REMOVED}.
     *
     * @return Тип события удаления сценария.
     */
    @Override
    @Schema(description = "Тип события удаления сценария.")
    public HubEventType getType() {
        return HubEventType.SCENARIO_REMOVED;
    }
}