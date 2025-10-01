package ru.yandex.practicum.telemetry.collector.service.handler.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.DeviceAddedEventProto;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.DeviceTypeAvro;
import ru.yandex.practicum.telemetry.collector.service.KafkaEventProducer;
import ru.yandex.practicum.telemetry.collector.utils.EnumMapper;

/**
 * Обработчик события от датчика климата
 */
@Component
public class DeviceAddedHubEventHandler extends BaseHubEventHandler<DeviceAddedEventAvro> {

    public DeviceAddedHubEventHandler(KafkaEventProducer producer) {
        super(producer);
    }

    @Override
    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.DEVICE_ADDED;
    }

    @Override
    protected DeviceAddedEventAvro mapToAvro(HubEventProto eventProto) {
        DeviceAddedEventProto protoPayload = eventProto.getDeviceAdded();
        return DeviceAddedEventAvro.newBuilder()
                .setId(protoPayload.getId())
                .setType(EnumMapper.map(protoPayload.getType(), DeviceTypeAvro.class))
                .build();
    }
}