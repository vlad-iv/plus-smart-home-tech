package ru.yandex.practicum.telemetry.collector.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Перечисление возможных типов действий при срабатывании условия активации сценария.
 */
@Schema(description = "Перечисление возможных типов действий при срабатывании условия активации сценария.")
public enum ActionType {

    /**
     * Активировать.
     */
    @Schema(description = "Активировать.")
    ACTIVATE,

    /**
     * Деактивировать.
     */
    @Schema(description = "Деактивировать.")
    DEACTIVATE,

    /**
     * Изменить значение на противоположное.
     */
    @Schema(description = "Изменить значение на противоположное.")
    INVERSE,

    /**
     * Установить значение.
     */
    @Schema(description = "Установить значение.")
    SET_VALUE
}