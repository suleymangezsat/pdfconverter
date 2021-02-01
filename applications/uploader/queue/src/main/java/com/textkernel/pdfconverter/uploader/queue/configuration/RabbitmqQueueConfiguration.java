package com.textkernel.pdfconverter.uploader.queue.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.textkernel.pdfconverter.uploader.core.properties.RabbitmqProperties;

@Configuration
public class RabbitmqQueueConfiguration {
	@Bean
	public Queue convertingQueue(RabbitmqProperties rabbitmqProperties) {
		return new Queue(rabbitmqProperties.getConvertingQueueName());
	}

	@Bean
	public Queue statusUpdatingQueue(RabbitmqProperties rabbitProperties) {
		return new Queue(rabbitProperties.getStatusUpdatingQueueName());
	}
}
