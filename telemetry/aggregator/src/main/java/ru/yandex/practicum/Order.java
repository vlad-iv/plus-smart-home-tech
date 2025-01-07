package ru.yandex.practicum;

@Entity
public class Order {

	@Version
	int version;
}

// update .... where id = and version =
// 0 -> Спринг выбросит исключение оптимистической блокировки

// Создать нагрузку - Apache JMeter
// kibana - создать дашборд работы приложения
