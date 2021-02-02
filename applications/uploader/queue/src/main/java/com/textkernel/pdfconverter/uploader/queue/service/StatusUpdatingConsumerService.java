package com.textkernel.pdfconverter.uploader.queue.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.textkernel.pdfconverter.uploader.core.service.FileStorageService;
import com.textkernel.pdfconverter.uploader.queue.dto.StatusUpdatingPayload;

@Service
public class StatusUpdatingConsumerService {
	private static final Logger logger = LoggerFactory.getLogger(ConvertingProducerService.class);
	private final FileStorageService fileStorageService;

	public StatusUpdatingConsumerService(FileStorageService fileStorageService) {
		this.fileStorageService = fileStorageService;
	}

	@RabbitListener(queues = "#{statusUpdatingQueue.name}")
	public void receiveFilesToUpdateStatus(@Payload StatusUpdatingPayload payload) {
		logger.info("Status update consumer received a message: {}", payload.getId());

		fileStorageService.update(payload.getId(), payload.getStatus(), payload.getConvertingResult(), payload.getMessage());

		logger.info("Message successfully stored: {}", payload.getId());
	}
}
