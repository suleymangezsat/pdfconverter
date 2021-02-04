package com.textkernel.pdfconverter.uploader.queue.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.Queue;

import com.textkernel.pdfconverter.uploader.core.properties.RabbitmqProperties;

class QueueConfigurationTest {
	@Mock
	private RabbitmqProperties properties;

	private QueueConfiguration configuration;
	private AutoCloseable closeable;

	@BeforeEach
	void setup() {
		closeable = MockitoAnnotations.openMocks(this);
		configuration = new QueueConfiguration();

		when(properties.getDirectExchangeName()).thenReturn("exchange");
	}

	@AfterEach
	void closeService() throws Exception {
		closeable.close();
	}

	@Test
	void statusUpdatingQueue() {
		when(properties.getStatusUpdatingQueueName()).thenReturn("status-update");
		Queue queue = configuration.statusUpdatingQueue(properties);
		assertNotNull(queue);
		assertEquals("status-update", queue.getName());
	}

	@Test
	void convertingQueue() {
		when(properties.getConvertingQueueName()).thenReturn("convert");
		Queue queue = configuration.convertingQueue(properties);
		assertNotNull(queue);
		assertEquals("convert", queue.getName());
		assertEquals("exchange", queue.getArguments().get("x-dead-letter-exchange"));
		assertEquals("convert.dlx", queue.getArguments().get("x-dead-letter-routing-key"));
	}

	@Test
	void convertingDLQueue() {
		when(properties.getConvertingQueueName()).thenReturn("convert");
		when(properties.getConvertingRouteName()).thenReturn("convert-route");
		Queue queue = configuration.convertingDLQueue(properties);
		assertNotNull(queue);
		assertEquals("convert.dlx", queue.getName());
		assertEquals("exchange", queue.getArguments().get("x-dead-letter-exchange"));
		assertEquals("convert-route", queue.getArguments().get("x-dead-letter-routing-key"));
	}
}