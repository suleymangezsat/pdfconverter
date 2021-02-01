package com.textkernel.pdfconverter.converter.queue.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.textkernel.pdfconverter.converter.core.dto.ConvertingResult;
import com.textkernel.pdfconverter.converter.core.dto.OriginalFile;
import com.textkernel.pdfconverter.converter.core.service.ConsumerService;
import com.textkernel.pdfconverter.converter.core.service.ConverterService;
import com.textkernel.pdfconverter.converter.core.service.ProducerService;
import com.textkernel.pdfconverter.converter.queue.dto.ConvertingQueuePayload;
import com.textkernel.pdfconverter.converter.queue.dto.StatusUpdatingQueuePayload;

@Service
public class RabbitmqConsumerService implements ConsumerService {
	private final ConverterService converterService;

	private final ProducerService producerService;


	public RabbitmqConsumerService(ConverterService converterService, RabbitmqProducerService rabbitmqProducerService, ProducerService producerService) {
		this.converterService = converterService;
		this.producerService = producerService;

	}

	@RabbitListener(queues = "#{convertingQueue.name}")
	public void receiveFilesToConvert(@Payload ConvertingQueuePayload file) {
		sendFileToConvert(file);
	}

	@Override
	public void sendFileToConvert(OriginalFile file) {
		ConvertingResult convertingResult = converterService.convert(file);
		producerService.sendToUpdateStatus(createPayload(convertingResult, file.getId()));
	}

	private StatusUpdatingQueuePayload createPayload(ConvertingResult convertingResult, String fileId) {
		StatusUpdatingQueuePayload statusUpdatingQueuePayload = new StatusUpdatingQueuePayload();
		statusUpdatingQueuePayload.setId(fileId);
		statusUpdatingQueuePayload.setTextPages(convertingResult.getTextPages());
		statusUpdatingQueuePayload.setStatus(convertingResult.getStatus());
		statusUpdatingQueuePayload.setErrorMessages(convertingResult.getErrorMessages());
		return statusUpdatingQueuePayload;
	}
}
