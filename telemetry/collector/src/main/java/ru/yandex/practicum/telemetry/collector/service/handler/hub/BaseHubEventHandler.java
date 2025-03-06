package ru.yandex.practicum.telemetry.collector.service.handler.hub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.telemetry.collector.model.HubEvent;
import ru.yandex.practicum.telemetry.collector.service.KafkaEventProducer;
import ru.yandex.practicum.telemetry.collector.service.handler.HubEventHandler;

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
    protected abstract T mapToAvro(HubEvent event);

    /**
     * Обрабатывает событие от датчика и сохраняет его в топик Kafka.
     *
     * @param event Событие от датчика
     */
    @Override
    public void handle(HubEvent event) {
        // Проверка соответствия типа события ожидаемому типу обработчика
        if (!event.getType().equals(getMessageType())) {
            throw new IllegalArgumentException("Неизвестный тип события: " + event.getType());
        }

        // Преобразование события в Avro-запись
        T payload = mapToAvro(event);

        HubEventAvro eventAvro = HubEventAvro.newBuilder()
                .setHubId(event.getHubId())
                .setTimestamp(event.getTimestamp())
                .setPayload(payload)
                .build();

        producer.send(eventAvro, event.getHubId(), event.getTimestamp(), HUBS_EVENTS);
    }
}