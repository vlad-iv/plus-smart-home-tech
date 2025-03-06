package ru.yandex.practicum.telemetry.collector.service.handler.sensor;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.telemetry.event.MotionSensorAvro;
import ru.yandex.practicum.telemetry.collector.model.MotionSensorEvent;
import ru.yandex.practicum.telemetry.collector.model.SensorEvent;
import ru.yandex.practicum.telemetry.collector.model.SensorEventType;
import ru.yandex.practicum.telemetry.collector.service.KafkaEventProducer;

/**
 * Обработчик события от датчика движения
 */
@Service
public class MotionSensorEventHandler extends BaseSensorEventHandler<MotionSensorAvro> {
    public MotionSensorEventHandler(KafkaEventProducer producer) {
        super(producer);
    }

    @Override
    public SensorEventType getMessageType() {
        return SensorEventType.MOTION_SENSOR_EVENT;
    }

    @Override
    protected MotionSensorAvro mapToAvro(SensorEvent event) {
        MotionSensorEvent payload = (MotionSensorEvent) event;
        return MotionSensorAvro.newBuilder()
                .setLinkQuality(payload.getLinkQuality())
                .setVoltage(payload.getVoltage())
                .setMotion(payload.isMotion())
                .build();
    }
}
