package com.textkernel.pdfconverter.uploader.queue.configuration;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.textkernel.pdfconverter.uploader.core.properties.RabbitmqProperties;

@Configuration
public class RabbitmqExchangeConfiguration {
	@Bean
	public DirectExchange direct(RabbitmqProperties rabbitmqProperties) {
		return new DirectExchange(rabbitmqProperties.getDirectExchangeName());
	}

}
