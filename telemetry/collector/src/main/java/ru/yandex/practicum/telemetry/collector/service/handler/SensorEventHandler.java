package ru.yandex.practicum.telemetry.collector.service.handler;

import ru.yandex.practicum.telemetry.collector.model.SensorEvent;
import ru.yandex.practicum.telemetry.collector.model.SensorEventType;

// Интерфейс объединяющий все хендлеры для SensorEvent событий
// Благодаря ему, мы сможем внедрить все хендлеры в виде списка
// в компонент, который будет распределять получаемые события по
// их обработчикам
public interface SensorEventHandler {
    SensorEventType getMessageType();

    void handle(SensorEvent event);
}