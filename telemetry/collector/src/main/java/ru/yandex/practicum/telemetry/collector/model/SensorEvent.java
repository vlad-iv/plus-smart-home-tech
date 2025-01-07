package ru.yandex.practicum.telemetry.collector.model;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.EXISTING_PROPERTY,
		property = "type",
		defaultImpl = SensorEventType.class
)
@JsonSubTypes({
		@JsonSubTypes.Type(value = LightSensorEvent.class, name = "LIGHT_SENSOR_EVENT"),
})
@Getter
@Setter
@ToString
public abstract class SensorEvent {

	@NotBlank
	private String id;
	@NotBlank
	private String hubId;
	private Instant timestamp = Instant.now();

	@NotNull
	public abstract SensorEventType getType();
}
