package com.textkernel.pdfconverter.uploader.queue.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.DirectExchange;

import com.textkernel.pdfconverter.uploader.core.properties.RabbitmqProperties;

class ExchangeConfigurationTest {

	private ExchangeConfiguration configuration;

	@BeforeEach
	void setup() {
		configuration = new ExchangeConfiguration();
	}

	@Test
	void directExchange_Success() {
		RabbitmqProperties properties = mock(RabbitmqProperties.class);
		when(properties.getDirectExchangeName()).thenReturn("exchange");
		DirectExchange exchange = configuration.directExchange(properties);
		assertNotNull(exchange);
		assertEquals("exchange", exchange.getName());
	}
}