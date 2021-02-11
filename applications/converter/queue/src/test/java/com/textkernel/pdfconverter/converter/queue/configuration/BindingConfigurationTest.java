package com.textkernel.pdfconverter.converter.queue.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;

import com.textkernel.pdfconverter.converter.core.properties.RabbitmqProperties;

class BindingConfigurationTest {
	private static final String EXCHANGE = "exhange";
	private static final String QUEUE = "queue";

	@Mock
	private DirectExchange exchange;
	@Mock
	private Queue queue;
	@Mock
	private RabbitmqProperties properties;

	private BindingConfiguration configuration;
	private AutoCloseable closeable;

	@BeforeEach
	void setup() {
		closeable = MockitoAnnotations.openMocks(this);
		configuration = new BindingConfiguration();

		when(exchange.getName()).thenReturn(EXCHANGE);
		when(queue.getName()).thenReturn(QUEUE);
	}

	@AfterEach
	void closeService() throws Exception {
		closeable.close();
	}

	@Test
	void bindingToStatusUpdating_Success() {
		when(properties.getStatusUpdatingRouteName()).thenReturn("route-status-update");
		Binding binding = configuration.bindingToStatusUpdating(exchange, queue, properties);
		assertNotNull(binding);
		assertEquals(EXCHANGE, binding.getExchange());
		assertEquals("route-status-update", binding.getRoutingKey());
	}

	@Test
	void bindingToConverting_Success() {
		when(properties.getConvertingRouteName()).thenReturn("route-convert");
		Binding binding = configuration.bindingToConverting(exchange, queue, properties);
		assertNotNull(binding);
		assertEquals(EXCHANGE, binding.getExchange());
		assertEquals("route-convert", binding.getRoutingKey());
	}

	@Test
	void bindingToConvertingDL_Success() {
		Binding binding = configuration.bindingToConvertingDL(exchange, queue);
		assertNotNull(binding);
		assertEquals(EXCHANGE, binding.getExchange());
		assertEquals(QUEUE, binding.getRoutingKey());
	}
}