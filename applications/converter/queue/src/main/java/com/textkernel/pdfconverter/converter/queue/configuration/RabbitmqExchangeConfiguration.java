package com.textkernel.pdfconverter.converter.queue.configuration;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.textkernel.pdfconverter.converter.core.properties.RabbitmqProperties;

@Configuration
public class RabbitmqExchangeConfiguration {
	@Bean
	public DirectExchange direct(RabbitmqProperties rabbitProperties) {
		return new DirectExchange(rabbitProperties.getDirectExchangeName());
	}

}
