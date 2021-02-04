package com.textkernel.pdfconverter.uploader.queue.configuration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.support.converter.MessageConverter;

class ProducerConfigurationTest {

	@Test
	void producerMessageConverter() {
		MessageConverter messageConverter = new ProducerConfiguration().producerMessageConverter();
		assertNotNull(messageConverter);
	}
}