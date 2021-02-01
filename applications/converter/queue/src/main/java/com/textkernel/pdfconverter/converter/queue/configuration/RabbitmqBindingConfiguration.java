package com.textkernel.pdfconverter.converter.queue.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.textkernel.pdfconverter.converter.core.properties.RabbitmqProperties;

@Configuration
public class RabbitmqBindingConfiguration {
	@Bean
	public Binding bindingToStatusUpdating(DirectExchange direct,
			Queue statusUpdatingQueue, RabbitmqProperties rabbitmqProperties) {
		return BindingBuilder.bind(statusUpdatingQueue)
				.to(direct)
				.with(rabbitmqProperties.getStatusUpdatingRouteName());
	}
}
