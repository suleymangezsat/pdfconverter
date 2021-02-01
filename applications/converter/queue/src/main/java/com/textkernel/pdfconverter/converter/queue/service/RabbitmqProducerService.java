package com.textkernel.pdfconverter.converter.queue.service;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.textkernel.pdfconverter.converter.core.dto.ConvertedFile;
import com.textkernel.pdfconverter.converter.core.properties.RabbitmqProperties;
import com.textkernel.pdfconverter.converter.core.service.ProducerService;

@Service
public class RabbitmqProducerService implements ProducerService {
	private final RabbitmqProperties rabbitmqProperties;
	private final RabbitTemplate rabbitTemplate;
	private final DirectExchange directExchange;

	public RabbitmqProducerService(RabbitmqProperties rabbitmqProperties, RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
		this.rabbitmqProperties = rabbitmqProperties;
		this.rabbitTemplate = rabbitTemplate;
		this.directExchange = directExchange;
	}


	@Override
	public void sendToUpdateStatus(ConvertedFile convertedFile) {
		this.rabbitTemplate.convertAndSend(directExchange.getName(), rabbitmqProperties.getStatusUpdatingRouteName(), convertedFile);
	}
}
