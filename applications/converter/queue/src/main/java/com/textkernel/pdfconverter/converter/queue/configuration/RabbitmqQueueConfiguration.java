package com.textkernel.pdfconverter.converter.queue.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.textkernel.pdfconverter.converter.core.properties.RabbitmqProperties;

@Configuration
public class RabbitmqQueueConfiguration {
	@Bean
	public Queue convertingQueue(RabbitmqProperties rabbitProperties) {
		return new Queue(rabbitProperties.getConvertingQueueName());
	}

	@Bean
	public Queue statusUpdatingQueue(RabbitmqProperties rabbitProperties) {
		return new Queue(rabbitProperties.getStatusUpdatingQueueName());
	}
}
