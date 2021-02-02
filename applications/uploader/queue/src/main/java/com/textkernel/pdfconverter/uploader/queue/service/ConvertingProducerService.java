package com.textkernel.pdfconverter.uploader.queue.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.textkernel.pdfconverter.uploader.core.properties.RabbitmqProperties;
import com.textkernel.pdfconverter.uploader.core.service.ProducerService;
import com.textkernel.pdfconverter.uploader.queue.dto.ConvertingPayload;

@Service
public class ConvertingProducerService implements ProducerService {
	private static final Logger logger = LoggerFactory.getLogger(ConvertingProducerService.class);

	private final RabbitTemplate rabbitTemplate;

	private final DirectExchange directExchange;

	private final RabbitmqProperties rabbitmqProperties;

	public ConvertingProducerService(RabbitTemplate rabbitTemplate, DirectExchange directExchange, RabbitmqProperties rabbitmqProperties) {
		this.rabbitTemplate = rabbitTemplate;
		this.directExchange = directExchange;
		this.rabbitmqProperties = rabbitmqProperties;
	}

	@Override
	public void sendFileToConvertingQueue(String id, byte[] resource, String contentType) {
		ConvertingPayload fileMessage = createPayload(id, resource, contentType);
		logger.info("Sending message to converting queue {}", fileMessage.getId());
		this.rabbitTemplate.convertAndSend(directExchange.getName(), rabbitmqProperties.getConvertingRouteName(), fileMessage);

	}

	private ConvertingPayload createPayload(String id, byte[] resource, String contentType) {
		ConvertingPayload fileMessage = new ConvertingPayload();
		fileMessage.setId(id);
		fileMessage.setResource(resource);
		fileMessage.setContentType(contentType);
		return fileMessage;
	}
}
