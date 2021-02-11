package com.textkernel.pdfconverter.uploader.queue.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.textkernel.pdfconverter.uploader.core.properties.RabbitmqProperties;

/**
 * Configures RabbitMQ queues
 */
@Configuration
public class QueueConfiguration {
	private static final String DL_QUEUE_SUFFIX = ".dlx";

	@Bean
	public Queue statusUpdatingQueue(RabbitmqProperties rabbitProperties) {
		return new Queue(rabbitProperties.getStatusUpdatingQueueName());
	}

	@Bean
	public Queue convertingQueue(RabbitmqProperties rabbitProperties) {
		return QueueBuilder.durable(rabbitProperties.getConvertingQueueName())
				.deadLetterExchange(rabbitProperties.getDirectExchangeName())
				.deadLetterRoutingKey(rabbitProperties.getConvertingQueueName() + DL_QUEUE_SUFFIX)
				.build();
	}

	@Bean
	public Queue convertingDLQueue(RabbitmqProperties rabbitProperties) {
		return QueueBuilder.durable(rabbitProperties.getConvertingQueueName() + DL_QUEUE_SUFFIX)
				.deadLetterExchange(rabbitProperties.getDirectExchangeName())
				.deadLetterRoutingKey(rabbitProperties.getConvertingRouteName())
				.ttl(rabbitProperties.getConvertingTtl())
				.build();
	}

}
