package com.textkernel.pdfconverter.converter.queue.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import com.textkernel.pdfconverter.converter.core.constant.Status;
import com.textkernel.pdfconverter.converter.core.dto.ConvertingResult;
import com.textkernel.pdfconverter.converter.core.service.ConverterService;
import com.textkernel.pdfconverter.converter.queue.dto.ConvertingPayload;
import com.textkernel.pdfconverter.converter.queue.dto.StatusUpdatingPayload;

/**
 * Converting consumer service responsible for converting pdf file to plain texst asynchronously through RabbitMQ.
 */
@Service
public class ConvertingConsumerService {
	private static final Logger logger = LoggerFactory.getLogger(ConvertingConsumerService.class);

	private final ConverterService converterService;

	public ConvertingConsumerService(ConverterService converterService) {
		this.converterService = converterService;
	}

	/**
	 * It consumes converting converting queue and processes messages by calling {@link ConverterService#convert} method.
	 *
	 * After processing the input it produces a {@link StatusUpdatingPayload} message to status updating queue.
	 *
	 * If there is an error when consuming the message it redirects the message and exception to {@link com.textkernel.pdfconverter.converter.queue.errorhandler.ConvertingTaskListenerErrorHandler}
	 *
	 * @param file
	 *        {@link ConvertingPayload} message to be processed
	 * @return {@link StatusUpdatingPayload} message to be consumed for updating database record
	 */
	@RabbitListener(queues = "#{convertingQueue.name}", errorHandler = "convertingTaskListenerErrorHandler")
	@SendTo("#{statusUpdatingQueue.getName()}")
	public StatusUpdatingPayload receiveFilesToConvert(@Payload ConvertingPayload file) {
		logger.info("Received a message for converting: {}", file.getId());

		ConvertingResult convertingResult = converterService.convert(file);
		return new StatusUpdatingPayload(file.getId(), convertingResult, Status.SUCCESS, null);
	}
}
