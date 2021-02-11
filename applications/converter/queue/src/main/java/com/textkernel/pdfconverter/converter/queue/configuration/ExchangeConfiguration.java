package com.textkernel.pdfconverter.converter.queue.configuration;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.textkernel.pdfconverter.converter.core.properties.RabbitmqProperties;

/**
 * Configures RabbitMQ exchanges
 */
@Configuration
public class ExchangeConfiguration {
	@Bean
	public DirectExchange directExchange(RabbitmqProperties rabbitProperties) {
		return ExchangeBuilder.directExchange(rabbitProperties.getDirectExchangeName()).durable(true).build();
	}


}
