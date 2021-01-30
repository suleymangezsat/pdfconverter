package com.textkernel.pdfconverter.uploader.queue.service;


import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.textkernel.pdfconverter.uploader.core.properties.RabbitmqProperties;
import com.textkernel.pdfconverter.uploader.core.service.ProducerService;
import com.textkernel.pdfconverter.uploader.queue.dto.FileMessage;

@Service
public class RabbitmqProducerService implements ProducerService {
	private final RabbitTemplate template;

	private final DirectExchange direct;

	private final RabbitmqProperties rabbitmqProperties;

	public RabbitmqProducerService(RabbitTemplate template, DirectExchange direct, RabbitmqProperties rabbitmqProperties) {
		this.template = template;
		this.direct = direct;
		this.rabbitmqProperties = rabbitmqProperties;
	}

	@Override
	public void sendFileToConvert(String id, byte[] resource, String contentType) {
		FileMessage fileMessage = createFileMessage(id, resource, contentType);
		this.template.convertAndSend(direct.getName(), rabbitmqProperties.getConvertingRouteName(), fileMessage);
		System.out.println(" [x] Sent '" + fileMessage.getId() + "'");
	}

	private FileMessage createFileMessage(String id, byte[] resource, String contentType) {
		FileMessage fileMessage = new FileMessage();
		fileMessage.setId(id);
		fileMessage.setResource(resource);
		fileMessage.setContentType(contentType);
		return fileMessage;
	}
}
