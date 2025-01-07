package ru.yandex.practicum.telemetry.collector.service;

import static ru.yandex.practicum.telemetry.collector.configuration.KafkaConfig.TopicType;

import java.time.Duration;
import java.time.Instant;
import java.util.EnumMap;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.telemetry.collector.configuration.KafkaConfig;

/**
 * Класс для отправки сообщений в кафку
 */
@Slf4j
@Component
public class KafkaEventProducer implements AutoCloseable {

    protected final KafkaProducer<String, SpecificRecordBase> producer;
    protected final EnumMap<TopicType, String> topics;

    /**
     * Конструктор класса.
     *
     * @param kafkaConfig Класс содержащий настройки для работы с kafka
     */
    public KafkaEventProducer(KafkaConfig kafkaConfig) {
        this.topics = kafkaConfig.getProducer().getTopics();

        // Создаём продюсера используя настройки из конфигурации приложения
        this.producer = new KafkaProducer<>(kafkaConfig.getProducer().getProperties());
    }

    /**
     * Обрабатывает событие от датчика и сохраняет его в топик Kafka.
     * @param event     Событие от датчика
     * @param hubId     Идентификатор хаба, в котором зарегистрирован датчик
     * @param timestamp Метка времени, когда произошло событие
     * @param topicType Тип топика который нужно использовать для отправки сообщения
     */
    public void send(SpecificRecordBase event, String hubId, Instant timestamp, TopicType topicType) {
        String topic = topics.get(topicType);
        ProducerRecord<String, SpecificRecordBase> record = new ProducerRecord<>(
                topic,
                null,
                timestamp.toEpochMilli(),
                hubId,
                event
        );

        log.trace("Сохраняю событие {} связанное с хабом {} в топик {}",
                event.getClass().getSimpleName(), hubId, topic);

        producer.send(record);
    }

    /**
     * Метод для закрытия ресурсов, связанных с обработчиком.
     * Завершает отправку сообщений в Kafka и закрывает продюсера.
     */
    @Override
    public void close() {
        producer.flush();
        producer.close(Duration.ofSeconds(10));
    }
}