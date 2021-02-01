package com.textkernel.pdfconverter.uploader.queue.service;


import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.textkernel.pdfconverter.uploader.core.properties.RabbitmqProperties;
import com.textkernel.pdfconverter.uploader.core.service.ProducerService;
import com.textkernel.pdfconverter.uploader.queue.dto.ConvertingQueuePayload;

@Service
public class RabbitmqProducerService implements ProducerService {
	private final RabbitTemplate rabbitTemplate;

	private final DirectExchange directExchange;

	private final RabbitmqProperties rabbitmqProperties;

	public RabbitmqProducerService(RabbitTemplate rabbitTemplate, DirectExchange directExchange, RabbitmqProperties rabbitmqProperties) {
		this.rabbitTemplate = rabbitTemplate;
		this.directExchange = directExchange;
		this.rabbitmqProperties = rabbitmqProperties;
	}

	@Override
	public void sendFileToConvertingQueue(String id, byte[] resource, String contentType) {
		ConvertingQueuePayload fileMessage = createPayload(id, resource, contentType);
		this.rabbitTemplate.convertAndSend(directExchange.getName(), rabbitmqProperties.getConvertingRouteName(), fileMessage);
	}

	private ConvertingQueuePayload createPayload(String id, byte[] resource, String contentType) {
		ConvertingQueuePayload fileMessage = new ConvertingQueuePayload();
		fileMessage.setId(id);
		fileMessage.setResource(resource);
		fileMessage.setContentType(contentType);
		return fileMessage;
	}
}
