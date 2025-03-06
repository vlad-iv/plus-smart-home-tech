package ru.yandex.practicum.telemetry.collector.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

/**
 * Абстрактный класс для представления событий датчиков.
 * Используется для различных типов событий датчиков, таких как климатический датчик, датчик освещенности и т.д.
 */
@Getter
@Setter
@ToString
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        defaultImpl = SensorEventType.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClimateSensorEvent.class, name = "CLIMATE_SENSOR_EVENT"),
        @JsonSubTypes.Type(value = LightSensorEvent.class, name = "LIGHT_SENSOR_EVENT"),
        @JsonSubTypes.Type(value = MotionSensorEvent.class, name = "MOTION_SENSOR_EVENT"),
        @JsonSubTypes.Type(value = SwitchSensorEvent.class, name = "SWITCH_SENSOR_EVENT"),
        @JsonSubTypes.Type(value = TemperatureSensorEvent.class, name = "TEMPERATURE_SENSOR_EVENT")
})
@Schema(description = "Абстрактный класс для представления событий датчиков. Используется для различных типов событий датчиков, таких как климатический датчик, датчик освещенности и т.д.",
        subTypes = {
                ClimateSensorEvent.class, LightSensorEvent.class,
                MotionSensorEvent.class, SwitchSensorEvent.class,
                TemperatureSensorEvent.class
        })
public abstract class SensorEvent {

    /**
     * Идентификатор события датчика.
     */
    @Schema(description = "Идентификатор события датчика.")
    @NotBlank
    private String id;

    /**
     * Идентификатор хаба, связанного с событием.
     */
    @Schema(description = "Идентификатор хаба, связанного с событием.")
    @NotNull
    private String hubId;

    /**
     * Временная метка события.
     * По умолчанию устанавливается текущее время.
     */
    @Schema(description = "Временная метка события. По умолчанию устанавливается текущее время.")
    private Instant timestamp = Instant.now();

    /**
     * Возвращает тип события, который определяет конкретный тип события датчика.
     *
     * @return Тип события датчика.
     */
    @NotNull
    @Schema(description = "Тип события, который определяет конкретный тип события датчика.")
    public abstract SensorEventType getType();
}