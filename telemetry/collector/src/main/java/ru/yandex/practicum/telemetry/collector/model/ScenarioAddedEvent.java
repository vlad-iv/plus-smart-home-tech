package ru.yandex.practicum.telemetry.collector.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Событие добавления сценария в систему.
 * Содержит информацию о названии сценария, условиях и действиях.
 */
@Getter
@Setter
@ToString(callSuper = true)
@Schema(description = "Событие добавления сценария в систему. Содержит информацию о названии сценария, условиях и действиях.")
public class ScenarioAddedEvent extends HubEvent {

    /**
     * Название добавленного сценария.
     * Должно содержать не менее 3 символов.
     */
    @Schema(description = "Название добавленного сценария. Должно содержать не менее 3 символов.")
    @NotBlank
    @Size(min = 3)
    private String name;

    /**
     * Список условий, которые связаны со сценарием.
     * Не может быть пустым.
     */
    @Schema(description = "Список условий, которые связаны со сценарием. Не может быть пустым.")
    @NotEmpty
    private List<ScenarioCondition> conditions;

    /**
     * Список действий, которые должны быть выполнены в рамках сценария.
     * Не может быть пустым.
     */
    @Schema(description = "Список действий, которые должны быть выполнены в рамках сценария. Не может быть пустым.")
    @NotEmpty
    private List<DeviceAction> actions;

    /**
     * Возвращает тип события, всегда {@link HubEventType#SCENARIO_ADDED}.
     *
     * @return Тип события добавления сценария.
     */
    @Override
    @Schema(description = "Тип события добавления сценария.")
    public HubEventType getType() {
        return HubEventType.SCENARIO_ADDED;
    }
}