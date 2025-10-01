package ru.yandex.practicum.telemetry.collector.controller;

import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.yandex.practicum.grpc.telemetry.collector.CollectorControllerGrpc;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.telemetry.collector.service.handler.HubEventHandler;
import ru.yandex.practicum.telemetry.collector.service.handler.SensorEventHandler;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * gRPC-сервис для сбора событий от датчиков
 */
@GrpcService
public class EventController extends CollectorControllerGrpc.CollectorControllerImplBase {
    private final Map<SensorEventProto.PayloadCase, SensorEventHandler> sensorEventHandlers;
    private final Map<HubEventProto.PayloadCase, HubEventHandler> hubEventHandlers;

    /**
     * Конструктор класса.
     *
     * @param sensorEventHandlers Обработчики событий от датчиков. Это классы реализующие SensorEventHandler и являющиеся spring bean
     * @param hubEventHandlers    Обработчики событий от хабов. Это классы реализующие HubEventHandler и являющиеся spring bean
     */
    public EventController(Set<SensorEventHandler> sensorEventHandlers, Set<HubEventHandler> hubEventHandlers) {
        // Преобразовываем набор хендлеров в map, где ключ - тип события от конкретного датчика или hub'а
        // Это нужно для упрощения поиска подходящего хендлера во время обработки событий
        this.sensorEventHandlers = sensorEventHandlers.stream()
                .collect(Collectors.toMap(SensorEventHandler::getMessageType, Function.identity()));
        this.hubEventHandlers = hubEventHandlers.stream()
                .collect(Collectors.toMap(HubEventHandler::getMessageType, Function.identity()));
    }

    /**
     * Метод для обработки событий от датчиков.
     * Вызывается при получении нового события от grpc-клиента.
     *
     * @param request           Событие от датчика
     * @param responseObserver  Ответ для клиента
     */
    @Override
    public void collectSensorEvent(SensorEventProto request, StreamObserver<Empty> responseObserver) {
        try {
            // Проверяем есть ли обработчик для полученного события
            if (sensorEventHandlers.containsKey(request.getPayloadCase())) {
                // если обработчик найден - передаём событие ему на обработку
                sensorEventHandlers.get(request.getPayloadCase()).handle(request);
            } else {
                throw new IllegalArgumentException("Не могу найти обработчик для события " + request.getPayloadCase());
            }

            // После обработки события возвращаем ответ клиенту
            responseObserver.onNext(Empty.getDefaultInstance());
            // и завершаем обработку запроса
            responseObserver.onCompleted();
        } catch (Exception e) {
            // В случае исключения отправляем ошибку клиенту
            responseObserver.onError(new StatusRuntimeException(Status.fromThrowable(e)));
        }
    }

    /**
     * Метод для обработки событий от хаба.
     * Вызывается при получении нового события от grpc-клиента.
     *
     * @param request           Событие от хаба
     * @param responseObserver  Ответ для клиента
     */
    @Override
    public void collectHubEvent(HubEventProto request, StreamObserver<Empty> responseObserver) {
        try {
            // Проверяем есть ли обработчик для полученного события
            if (hubEventHandlers.containsKey(request.getPayloadCase())) {
                // если обработчик найден - передаём событие ему на обработку
                hubEventHandlers.get(request.getPayloadCase()).handle(request);
            } else {
                throw new IllegalArgumentException("Не могу найти обработчик для события " + request.getPayloadCase());
            }

            // После обработки события возвращаем ответ клиенту
            responseObserver.onNext(Empty.getDefaultInstance());
            // и завершаем обработку запроса
            responseObserver.onCompleted();
        } catch (Exception e) {
            // В случае исключения отправляем ошибку клиенту
            responseObserver.onError(new StatusRuntimeException(Status.fromThrowable(e)));
        }
    }
}