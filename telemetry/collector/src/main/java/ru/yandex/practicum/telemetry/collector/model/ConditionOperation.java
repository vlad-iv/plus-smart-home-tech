package ru.yandex.practicum.telemetry.collector.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Операции, которые могут быть использованы в условиях.
 */
@Schema(description = "Операции, которые могут быть использованы в условиях.")
public enum ConditionOperation {

    /**
     * Равно.
     */
    @Schema(description = "Равно.")
    EQUALS,

    /**
     * Больше чем.
     */
    @Schema(description = "Больше чем.")
    GREATER_THAN,

    /**
     * Меньше чем.
     */
    @Schema(description = "Меньше чем.")
    LOWER_THAN
}