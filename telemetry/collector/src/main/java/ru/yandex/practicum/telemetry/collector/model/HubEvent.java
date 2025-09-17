package ru.yandex.practicum.telemetry.collector.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

/**
 * Абстрактный класс для представления событий хаба.
 * Используется для различных типов событий, таких как добавление и удаление устройств или сценариев.
 */
@Getter
@Setter
@ToString
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        defaultImpl = ErrorEventType.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DeviceAddedEvent.class, name = "DEVICE_ADDED"),
        @JsonSubTypes.Type(value = DeviceRemovedEvent.class, name = "DEVICE_REMOVED"),
        @JsonSubTypes.Type(value = ScenarioAddedEvent.class, name = "SCENARIO_ADDED"),
        @JsonSubTypes.Type(value = ScenarioRemovedEvent.class, name = "SCENARIO_REMOVED")
})
@Schema(description = "Абстрактный класс для представления событий хаба.")
public abstract class HubEvent {

    /**
     * Идентификатор хаба, связанный с событием.
     */
    @Schema(description = "Идентификатор хаба, связанный с событием.")
    @NotNull
    private String hubId;

    /**
     * Временная метка события.
     * По умолчанию устанавливается текущее время.
     */
    @Schema(description = "Временная метка события. По умолчанию устанавливается текущее время.")
    private Instant timestamp = Instant.now();

    /**
     * Возвращает тип события, который определяет конкретный тип события.
     * Например, добавление устройства или удаление сценария.
     *
     * @return Тип события.
     */
    @NotNull
    @Schema(description = "Тип события, который определяет конкретный тип события, например, добавление устройства или удаление сценария.")
    public abstract HubEventType getType();
}