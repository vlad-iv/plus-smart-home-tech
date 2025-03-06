package ru.yandex.practicum.telemetry.collector.service.handler.hub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.telemetry.collector.service.KafkaEventProducer;
import ru.yandex.practicum.telemetry.collector.service.handler.HubEventHandler;

import java.time.Instant;

import static ru.yandex.practicum.telemetry.collector.configuration.KafkaConfig.TopicType.HUBS_EVENTS;

/**
 * Базовый класс для обработчиков событий от датчиков, работающих с Avro.
 * Реализует интерфейс SensorEventHandler.
 */
@Slf4j
@RequiredArgsConstructor
public abstract class BaseHubEventHandler<T extends SpecificRecordBase> implements HubEventHandler {
    protected final KafkaEventProducer producer;

    /**
     * Метод для преобразования сообщения из Protobuf в Avro.
     * Должен быть реализован в наследниках базового класса
     *
     * @param event Событие от датчика в формате Protobuf
     * @return событие от датчика в формате Avro
     */
    protected abstract T mapToAvro(HubEventProto event);

    /**
     * Обрабатывает событие от датчика и сохраняет его в топик Kafka.
     *
     * @param event Событие от датчика
     */
    @Override
    public void handle(HubEventProto event) {
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

        HubEventAvro eventAvro = HubEventAvro.newBuilder()
                .setHubId(event.getHubId())
                .setTimestamp(timestamp)
                .setPayload(payload)
                .build();

        producer.send(eventAvro, event.getHubId(), timestamp, HUBS_EVENTS);
    }
}