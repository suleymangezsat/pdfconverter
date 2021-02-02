package com.textkernel.pdfconverter.uploader.queue.configuration;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProducerConfiguration {
	@Bean
	public MessageConverter producerMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
}
