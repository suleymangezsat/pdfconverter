package com.textkernel.pdfconverter.converter.queue.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.textkernel.pdfconverter.converter.core.dto.Converted;
import com.textkernel.pdfconverter.converter.core.properties.RabbitmqProperties;
import com.textkernel.pdfconverter.converter.core.service.ProducerService;
import com.textkernel.pdfconverter.converter.queue.dto.StatusUpdatingPayload;

/**
 * {@inheritDoc}
 */
@Service
public class StatusUpdatingProducerService implements ProducerService {
	private static final Logger logger = LoggerFactory.getLogger(StatusUpdatingProducerService.class);

	private final RabbitmqProperties rabbitmqProperties;
	private final RabbitTemplate rabbitTemplate;

	public StatusUpdatingProducerService(RabbitmqProperties rabbitmqProperties, RabbitTemplate rabbitTemplate) {
		this.rabbitmqProperties = rabbitmqProperties;
		this.rabbitTemplate = rabbitTemplate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sendToUpdatingStatusTask(Converted converted) {
		StatusUpdatingPayload statusUpdatingQueuePayload = createPayload(converted);
		logger.info("Sending message to status updating queue : " + converted.getId());
		this.rabbitTemplate.convertAndSend(rabbitmqProperties.getDirectExchangeName(), rabbitmqProperties.getStatusUpdatingRouteName(), statusUpdatingQueuePayload);
	}


	private StatusUpdatingPayload createPayload(Converted converted) {
		StatusUpdatingPayload statusUpdatingQueuePayload = new StatusUpdatingPayload();
		statusUpdatingQueuePayload.setId(converted.getId());
		statusUpdatingQueuePayload.setConvertingResult(converted.getConvertingResult());
		statusUpdatingQueuePayload.setStatus(converted.getStatus());
		statusUpdatingQueuePayload.setMessage(converted.getMessage());
		return statusUpdatingQueuePayload;
	}
}
