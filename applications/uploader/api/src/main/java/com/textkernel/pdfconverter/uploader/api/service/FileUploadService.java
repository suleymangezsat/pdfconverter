package com.textkernel.pdfconverter.uploader.api.service;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.textkernel.pdfconverter.uploader.api.constant.ErrorMessage;
import com.textkernel.pdfconverter.uploader.api.dto.OriginalFileDto;
import com.textkernel.pdfconverter.uploader.api.exception.FileHandlingException;
import com.textkernel.pdfconverter.uploader.core.constant.Status;
import com.textkernel.pdfconverter.uploader.core.dto.FileTask;
import com.textkernel.pdfconverter.uploader.core.service.FileStorageService;
import com.textkernel.pdfconverter.uploader.core.service.ProducerService;


@Service
public class FileUploadService {
	private static final Logger logger = LoggerFactory.getLogger(FileUploadService.class);
	private final FileStorageService fileStorageService;
	private final ProducerService fileQueueService;

	public FileUploadService(FileStorageService fileStorageService, ProducerService fileQueueService) {
		this.fileStorageService = fileStorageService;
		this.fileQueueService = fileQueueService;
	}

	public List<FileTask> listAll() {
		return fileStorageService.getAll();
	}

	public FileTask upload(MultipartFile file) {
		String fileName = StringUtils.cleanPath(Optional.ofNullable(file.getOriginalFilename()).orElseThrow(() -> new FileHandlingException(ErrorMessage.FILE_BLANK_NAME_ERROR)));
		OriginalFileDto originalFile;
		try {
			originalFile = new OriginalFileDto(fileName, file.getBytes(), file.getContentType());
		} catch (IOException e) {
			throw new FileHandlingException(ErrorMessage.FILE_RESOLVING_ERROR);
		}

		FileTask uploadedFile = fileStorageService.create(originalFile, Status.INIT);

		logger.info("Stored file successfully : {}", uploadedFile.getId());
		fileQueueService.sendFileToConvertingQueue(uploadedFile.getId(), uploadedFile.getOriginalFile().getResource(), uploadedFile.getOriginalFile().getContentType());
		return uploadedFile;
	}
}
