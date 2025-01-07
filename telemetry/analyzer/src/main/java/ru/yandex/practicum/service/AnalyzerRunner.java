package ru.yandex.practicum.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AnalyzerRunner implements CommandLineRunner {
	final HubEventProcessor hubEventProcessor;
	final SnapshotProcessor snapshotProcessor;

	public AnalyzerRunner(HubEventProcessor hubEventProcessor, SnapshotProcessor snapshotProcessor) {
		this.hubEventProcessor = hubEventProcessor;
		this.snapshotProcessor = snapshotProcessor;
	}

	@Override
	public void run(String... args) throws Exception {
		// запускаем в отдельном потоке обработчик событий
		// от пользовательских хабов
		Thread hubEventsThread = new Thread(hubEventProcessor);
		hubEventsThread.setName("HubEventHandlerThread");
		hubEventsThread.start();

		// В текущем потоке начинаем обработку
		// снимков состояния датчиков
		snapshotProcessor.start();
	}
}
