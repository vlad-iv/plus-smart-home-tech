package ru.yandex.practicum.telemetry.collector.service.handler.sensor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.telemetry.collector.service.KafkaEventProducer;
import ru.yandex.practicum.telemetry.collector.service.handler.SensorEventHandler;

import java.time.Instant;

import static ru.yandex.practicum.telemetry.collector.configuration.KafkaConfig.TopicType.SENSORS_EVENTS;

/**
 * Базовый класс для обработчиков событий от датчиков, работающих с Avro.
 * Реализует интерфейс SensorEventHandler.
 */
@Slf4j
@RequiredArgsConstructor
public abstract class BaseSensorEventHandler<T extends SpecificRecordBase> implements SensorEventHandler {
    protected final KafkaEventProducer producer;

    /**
     * Метод для преобразования сообщения из Protobuf в Avro.
     * Должен быть реализован в наследниках базового класса
     *
     * @param event Событие от датчика в формате Protobuf
     * @return событие от датчика в формате Avro
     */
    protected abstract T mapToAvro(SensorEventProto event);

    /**
     * Обрабатывает событие от датчика и сохраняет его в топик Kafka.
     *
     * @param event Событие от датчика
     */
    @Override
    public void handle(SensorEventProto event) {
        // Проверка соответствия типа события ожидаемому типу обработчика
        if (!event.getPayloadCase().equals(getMessageType())) {
            throw new IllegalArgumentException("Неизвестный тип события: " + event.getPayloadCase());
        }

        // Преобразование события в Avro-запись
        T payload = mapToAvro(event);

        Instant timestamp = Instant.ofEpochSecond(
                event.getTimestamp().getSeconds(),
                event.getTimestamp().getNanos()
        );

        SensorEventAvro eventAvro = SensorEventAvro.newBuilder()
                .setHubId(event.getHubId())
                .setId(event.getId())
                .setTimestamp(timestamp)
                .setPayload(payload)
                .build();

        producer.send(eventAvro, event.getHubId(), timestamp, SENSORS_EVENTS);
    }
}