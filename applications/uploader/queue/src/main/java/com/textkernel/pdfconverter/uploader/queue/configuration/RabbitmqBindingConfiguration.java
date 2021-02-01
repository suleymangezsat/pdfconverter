package com.textkernel.pdfconverter.uploader.queue.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.textkernel.pdfconverter.uploader.core.properties.RabbitmqProperties;

@Configuration
public class RabbitmqBindingConfiguration {
	@Bean
	public Binding bindingToConverting(DirectExchange direct,
			Queue convertingQueue, RabbitmqProperties rabbitmqProperties) {
		return BindingBuilder.bind(convertingQueue)
				.to(direct)
				.with(rabbitmqProperties.getConvertingRouteName());
	}
}
