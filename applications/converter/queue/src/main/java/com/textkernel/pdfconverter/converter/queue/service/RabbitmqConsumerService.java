package com.textkernel.pdfconverter.converter.queue.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import com.textkernel.pdfconverter.converter.core.service.ConverterService;
import com.textkernel.pdfconverter.converter.queue.dto.FileMessageImpl;

@Service
public class RabbitmqConsumerService {
	private final ConverterService converterService;

	public RabbitmqConsumerService(ConverterService converterService) {
		this.converterService = converterService;
	}

	@RabbitListener(queues = "#{convertingQueue.name}")
	public void receiveFilesToConvert(@Payload FileMessageImpl file) {
		System.out.println(" [x] Received '" + file.getId() + "'");
		String plainText = converterService.getPlainText(file);
		System.out.println(" [x] Plaing text '" + plainText + "'");
	}

}
