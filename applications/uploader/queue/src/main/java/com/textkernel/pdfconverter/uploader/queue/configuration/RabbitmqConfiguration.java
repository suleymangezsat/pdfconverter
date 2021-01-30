package com.textkernel.pdfconverter.uploader.queue.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.textkernel.pdfconverter.uploader.core.properties.RabbitmqProperties;

@Configuration
public class RabbitmqConfiguration {

	@Bean
	public MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory rabbitConnectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(rabbitConnectionFactory);
		rabbitTemplate.setMessageConverter(messageConverter());
		return rabbitTemplate;
	}

	@Bean
	public DirectExchange direct(RabbitmqProperties rabbitmqProperties) {
		return new DirectExchange(rabbitmqProperties.getDirectExchangeName());
	}

	@Bean
	public Queue convertingQueue(RabbitmqProperties rabbitmqProperties) {
		return new Queue(rabbitmqProperties.getConvertingQueueName());
	}

	@Bean
	public Binding bindingToConverting(DirectExchange direct,
			Queue convertQueue, RabbitmqProperties rabbitmqProperties) {
		return BindingBuilder.bind(convertQueue)
				.to(direct)
				.with(rabbitmqProperties.getConvertingRouteName());
	}
}
