package com.textkernel.pdfconverter.converter.queue.configuration;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

import com.textkernel.pdfconverter.converter.core.properties.RabbitmqProperties;

@Configuration
public class RabbitmqConfiguration implements RabbitListenerConfigurer {

	@Bean
	public MessageConverter messageConverter() {
		return new MappingJackson2MessageConverter();
	}

	@Bean
	public DirectExchange direct(RabbitmqProperties rabbitProperties) {
		return new DirectExchange(rabbitProperties.getDirectExchangeName());
	}

	@Bean
	public Queue convertingQueue(RabbitmqProperties rabbitProperties) {
		return new Queue(rabbitProperties.getConvertingQueueName());
	}

	@Bean
	public MessageHandlerMethodFactory messageHandlerMethodFactory() {
		DefaultMessageHandlerMethodFactory defaultMessageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
		defaultMessageHandlerMethodFactory.setMessageConverter(messageConverter());
		return defaultMessageHandlerMethodFactory;
	}

	@Override
	public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
		registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
	}
}
