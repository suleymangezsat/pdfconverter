package com.textkernel.pdfconverter.uploader.queue.configuration;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configurations about RabbitMQ message processing
 */
@Configuration
public class ProducerConfiguration {

	/**
	 * @return a bean that tells how to (de)serialize RabbitMQ messages
	 */
	@Bean
	public Jackson2JsonMessageConverter producerMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
}
