package com.textkernel.pdfconverter.uploader.api.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.textkernel.pdfconverter.uploader.api.constant.ErrorMessage;
import com.textkernel.pdfconverter.uploader.api.dto.OriginalFileDto;
import com.textkernel.pdfconverter.uploader.api.exception.FileHandlingException;
import com.textkernel.pdfconverter.uploader.api.exception.FileValidationException;
import com.textkernel.pdfconverter.uploader.core.constant.Status;
import com.textkernel.pdfconverter.uploader.core.dto.FileTask;
import com.textkernel.pdfconverter.uploader.core.dto.OriginalFile;
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

	public List<FileTask> list(String[] ids) {
		return fileStorageService.get(Arrays.asList(ids));
	}

	public List<FileTask> upload(MultipartFile[] files) {
		List<OriginalFile> originalFiles = createOriginalFiles(files);
		List<FileTask> uploadedFiles = fileStorageService.create(originalFiles, Status.INIT);

		logger.info("Stored files successfully");
		uploadedFiles.forEach(uploadedFile -> fileQueueService.sendFileToConvertingQueue(uploadedFile.getId(), uploadedFile.getOriginalFile().getResource(), uploadedFile.getOriginalFile().getContentType()));
		return uploadedFiles;
	}

	private List<OriginalFile> createOriginalFiles(MultipartFile[] files) {
		return Arrays.stream(files).map(this::mapToOriginalFile).collect(Collectors.toList());
	}

	private OriginalFile mapToOriginalFile(MultipartFile file) {
		String fileName = StringUtils.cleanPath(Optional.ofNullable(file.getOriginalFilename()).orElseThrow(() -> new FileHandlingException(ErrorMessage.FILE_BLANK_NAME_ERROR)));
		OriginalFileDto originalFile;
		try {
			originalFile = new OriginalFileDto(fileName, file.getBytes(), file.getSize(), file.getContentType());
		} catch (IOException e) {
			throw new FileHandlingException(ErrorMessage.FILE_RESOLVING_ERROR);
		}
		return originalFile;
	}
}
