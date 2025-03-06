package ru.yandex.practicum.telemetry.collector.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.Data;

/**
 * Условие сценария, которое содержит информацию о датчике, типе условия, операции и значении.
 */
@Data
@Schema(description = "Условие сценария, которое содержит информацию о датчике, типе условия, операции и значении.")
public class ScenarioCondition {

    /**
     * Идентификатор датчика, связанного с условием.
     */
    @Schema(description = "Идентификатор датчика, связанного с условием.")
    private String sensorId;

    /**
     * Тип условия.
     */
    @Schema(description = "Тип условия.")
    private ConditionType type;

    /**
     * Операция, которая применяется в условии.
     */
    @Schema(description = "Операция, которая применяется в условии.")
    private ConditionOperation operation;

    /**
     * Значение, используемое в условии.
     */
    @Schema(description = "Значение, используемое в условии.")
    private Integer value;
}