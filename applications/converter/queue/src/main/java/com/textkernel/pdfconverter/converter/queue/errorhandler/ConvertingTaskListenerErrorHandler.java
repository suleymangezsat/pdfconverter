package com.textkernel.pdfconverter.converter.queue.errorhandler;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import com.textkernel.pdfconverter.converter.core.constant.Status;
import com.textkernel.pdfconverter.converter.core.exception.FatalException;
import com.textkernel.pdfconverter.converter.core.properties.RabbitmqProperties;
import com.textkernel.pdfconverter.converter.core.service.ProducerService;
import com.textkernel.pdfconverter.converter.queue.dto.ConvertingPayload;
import com.textkernel.pdfconverter.converter.queue.dto.StatusUpdatingPayload;

@Component
public class ConvertingTaskListenerErrorHandler implements RabbitListenerErrorHandler {
	private static final Logger logger = LoggerFactory.getLogger(ConvertingTaskListenerErrorHandler.class);
	private static final String X_DEATH_HEADER = "x-death";
	private final RabbitmqProperties rabbitmqProperties;
	private final ProducerService producerService;

	public ConvertingTaskListenerErrorHandler(RabbitmqProperties rabbitmqProperties, ProducerService producerService) {
		this.rabbitmqProperties = rabbitmqProperties;
		this.producerService = producerService;
	}

	@Override
	public Object handleError(Message amqpMessage, org.springframework.messaging.Message<?> message, ListenerExecutionFailedException exception) {
		int retries = getRetries(message.getHeaders());

		ConvertingPayload convertingPayload = (ConvertingPayload) message.getPayload();
		StatusUpdatingPayload statusUpdatingPayload = new StatusUpdatingPayload();
		statusUpdatingPayload.setId(convertingPayload.getId());

		logger.warn("Current retry count : {}, Max retry : {}", retries, rabbitmqProperties.getConvertingMaxRetryCount());
		if (exception.getCause() instanceof FatalException || retries == rabbitmqProperties.getConvertingMaxRetryCount()) {
			statusUpdatingPayload.setStatus(Status.FAILED);
			statusUpdatingPayload.setMessage(exception.getCause().getMessage());
			logger.warn("Failed converting task with message: {}", statusUpdatingPayload.getMessage());
			return statusUpdatingPayload;
		}

		statusUpdatingPayload.setStatus(Status.RETRY);
		statusUpdatingPayload.setMessage(String.format("Retrying ... (%s)", retries));
		logger.warn("Retry for converting. # of retries: {}, retry reason {}", retries, statusUpdatingPayload.getMessage());
		producerService.sendToUpdatingStatusTask(statusUpdatingPayload);

		throw exception;
	}

	@SuppressWarnings("unchecked")
	private int getRetries(MessageHeaders headers) {
		List<Map<String, ?>> xDeathHeader = (List<Map<String, ?>>) headers.get(X_DEATH_HEADER);
		if (xDeathHeader != null && !xDeathHeader.isEmpty()) {
			Long retries = (Long) xDeathHeader.get(0).get("count");
			return retries.intValue();
		}
		return 0;
	}
}