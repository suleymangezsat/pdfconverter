package com.textkernel.pdfconverter.converter.queue.configuration;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
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
	public MessageConverter producerMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	/**
	 * Creates a {@link RabbitTemplate} bean
	 *
	 * @param rabbitConnectionFactory
	 * 		connection factory bean use in the template
	 * @return {@link RabbitTemplate} bean
	 */
	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory rabbitConnectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(rabbitConnectionFactory);
		rabbitTemplate.setMessageConverter(producerMessageConverter());
		return rabbitTemplate;
	}
}
