package ru.yandex.practicum.telemetry.collector.service.handler;

import ru.yandex.practicum.telemetry.collector.model.HubEvent;
import ru.yandex.practicum.telemetry.collector.model.HubEventType;

// Интерфейс объединяющий все хендлеры для HubEvent событий
// Благодаря ему, мы сможем внедрить все хендлеры в виде списка
// в компонент, который будет распределять получаемые события по
// их обработчикам
public interface HubEventHandler {
    HubEventType getMessageType();

    void handle(HubEvent event);
}