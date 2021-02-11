package com.textkernel.pdfconverter.uploader.queue.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.textkernel.pdfconverter.uploader.core.properties.RabbitmqProperties;

/**
 * Configures RabbitMQ bindings
 */
@Configuration
public class BindingConfiguration {

	@Bean
	public Binding bindingToStatusUpdating(DirectExchange directExchange,
			Queue statusUpdatingQueue, RabbitmqProperties rabbitmqProperties) {
		return BindingBuilder.bind(statusUpdatingQueue)
				.to(directExchange)
				.with(rabbitmqProperties.getStatusUpdatingRouteName());
	}

	@Bean
	public Binding bindingToConverting(DirectExchange directExchange,
			Queue convertingQueue, RabbitmqProperties rabbitmqProperties) {
		return BindingBuilder.bind(convertingQueue)
				.to(directExchange)
				.with(rabbitmqProperties.getConvertingRouteName());
	}

	@Bean
	public Binding bindingToConvertingDL(DirectExchange directExchange, Queue convertingDLQueue) {
		return BindingBuilder.bind(convertingDLQueue)
				.to(directExchange)
				.with(convertingDLQueue.getName());
	}
}
