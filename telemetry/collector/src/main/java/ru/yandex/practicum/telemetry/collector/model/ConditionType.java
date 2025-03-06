package ru.yandex.practicum.telemetry.collector.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Типы условий, которые могут использоваться в сценариях.
 */
@Schema(description = "Типы условий, которые могут использоваться в сценариях.")
public enum ConditionType {

    /**
     * Условие на наличие/отсутствие движения.
     */
    @Schema(description = "Условие на наличие/отсутствие движения.")
    MOTION,

    /**
     * Условие на процент освещенности
     */
    @Schema(description = "Условие на процент освещенности")
    LUMINOSITY,

    /**
     * Условие на наличие состояния включено/выключено
     */
    @Schema(description = "Условие на наличие состояния включено/выключено")
    SWITCH,

    /**
     * Условие на показания температуры
     */
    @Schema(description = "Условие на показания температуры.")
    TEMPERATURE,

    /**
     * Условие на уровень CO2.
     */
    @Schema(description = "Условие на уровень CO2.")
    CO2LEVEL,

    /**
     * Условия на уровень влажности.
     */
    @Schema(description = "Условия на уровень влажности.")
    HUMIDITY
}