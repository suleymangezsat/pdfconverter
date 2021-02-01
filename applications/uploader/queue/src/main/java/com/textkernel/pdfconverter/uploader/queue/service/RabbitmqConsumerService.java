package com.textkernel.pdfconverter.uploader.queue.service;

import java.util.Optional;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.textkernel.pdfconverter.uploader.core.constant.FileStatus;
import com.textkernel.pdfconverter.uploader.core.dto.ConvertedFile;
import com.textkernel.pdfconverter.uploader.core.dto.File;
import com.textkernel.pdfconverter.uploader.core.service.ConsumerService;
import com.textkernel.pdfconverter.uploader.core.service.FileStorageService;
import com.textkernel.pdfconverter.uploader.queue.dto.StatusUpdatingQueuePayload;

@Service
public class RabbitmqConsumerService implements ConsumerService {
	private final FileStorageService fileStorageService;


	public RabbitmqConsumerService(FileStorageService fileStorageService) {
		this.fileStorageService = fileStorageService;

	}

	@RabbitListener(queues = "#{statusUpdatingQueue.name}")
	public void receiveFilesToUpdateStatus(@Payload StatusUpdatingQueuePayload file) {
		updateFileStatus(file);
	}

	@Override
	public void updateFileStatus(ConvertedFile convertedFile) {
		Optional<File> existingFile = fileStorageService.get(convertedFile.getId());
		existingFile.ifPresent((file)->fileStorageService.store(updateFile(convertedFile, file)));
	}

	private File updateFile(ConvertedFile converted, File existing) {
		existing.setTextPages(converted.getTextPages());
		existing.setConvertingStatus(converted.getStatus());
		existing.setErrorMessages(converted.getErrorMessages());
		existing.setStatus(FileStatus.CONVERTED);
		return existing;
	}
}
