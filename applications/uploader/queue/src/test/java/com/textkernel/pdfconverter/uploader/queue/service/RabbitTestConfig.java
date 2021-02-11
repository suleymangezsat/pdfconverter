package com.textkernel.pdfconverter.uploader.queue.service;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.DirectRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.test.TestRabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rabbitmq.client.Channel;

@Configuration
@EnableRabbit
public class RabbitTestConfig {
	private String lastMessage;

	@Bean
	public TestRabbitTemplate template(Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
		TestRabbitTemplate testRabbitTemplate = new TestRabbitTemplate(connectionFactory());
		testRabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
		return testRabbitTemplate;
	}

	@Bean
	public ConnectionFactory connectionFactory() {
		ConnectionFactory factory = mock(ConnectionFactory.class);
		Connection connection = mock(Connection.class);
		Channel channel = mock(Channel.class);
		when(factory.createConnection()).thenReturn(connection);
		when(connection.createChannel(anyBoolean())).thenReturn(channel);
		when(channel.isOpen()).thenReturn(true);
		return factory;
	}

	@Bean
	public DirectRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
		DirectRabbitListenerContainerFactory factory = new DirectRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		return factory;
	}

	public String getLastMessageContent() {
		return lastMessage;
	}

	@RabbitListener(queues = {"#{rabbitProperties.getConvertingRouteName()}"})
	public void messageConsumer(String message) {
		this.lastMessage = message;
	}
}
