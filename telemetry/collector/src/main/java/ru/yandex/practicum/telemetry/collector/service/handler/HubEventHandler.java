package ru.yandex.practicum.telemetry.collector.service.handler;

import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;

// Интерфейс объединяющий все хендлеры для HubEvent событий
// Благодаря ему, мы сможем внедрить все хендлеры в виде списка
// в компонент, который будет распределять получаемые события по
// их обработчикам
public interface HubEventHandler {
    HubEventProto.PayloadCase getMessageType();

    void handle(HubEventProto event);
}