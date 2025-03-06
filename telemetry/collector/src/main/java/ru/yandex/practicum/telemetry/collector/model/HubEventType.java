package ru.yandex.practicum.telemetry.collector.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Перечисление типов событий хаба.
 */
@Schema(description = "Перечисление типов событий хаба.")
public enum HubEventType {

    /**
     * Событие добавления устройства.
     */
    @Schema(description = "Событие добавления устройства.")
    DEVICE_ADDED,

    /**
     * Событие удаления устройства.
     */
    @Schema(description = "Событие удаления устройства.")
    DEVICE_REMOVED,

    /**
     * Событие добавления сценария.
     */
    @Schema(description = "Событие добавления сценария.")
    SCENARIO_ADDED,

    /**
     * Событие удаления сценария.
     */
    @Schema(description = "Событие удаления сценария.")
    SCENARIO_REMOVED
}
