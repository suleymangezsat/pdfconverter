package com.textkernel.pdfconverter.uploader.api.service;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.textkernel.pdfconverter.uploader.api.constant.ErrorMessage;
import com.textkernel.pdfconverter.uploader.api.dto.FileDto;
import com.textkernel.pdfconverter.uploader.core.constant.FileStatus;
import com.textkernel.pdfconverter.uploader.core.dto.File;
import com.textkernel.pdfconverter.uploader.core.service.ProducerService;
import com.textkernel.pdfconverter.uploader.core.service.FileStorageService;
import com.textkernel.pdfconverter.uploader.api.exception.FileHandlingException;


@Service
public class FileUploadService {

	private final FileStorageService fileStorageService;
	private final ProducerService fileQueueService;

	public FileUploadService(FileStorageService fileStorageService, ProducerService fileQueueService) {
		this.fileStorageService = fileStorageService;
		this.fileQueueService = fileQueueService;
	}

	public List<File> listAll() {
		return fileStorageService.getAll();
	}

	public File upload(MultipartFile file) {
		File uploadedFile = createFileDto(file);
		uploadedFile = fileStorageService.store(uploadedFile);
		fileQueueService.sendFileToConvertingQueue(uploadedFile.getId(), uploadedFile.getResource(), uploadedFile.getContentType());
		return uploadedFile;
	}

	private FileDto createFileDto(MultipartFile file) {
		String fileName = StringUtils.cleanPath(Optional.ofNullable(file.getOriginalFilename()).orElseThrow(() -> new FileHandlingException(ErrorMessage.FILE_BLANK_NAME_ERROR)));
		FileDto fileDto = new FileDto();
		fileDto.setName(fileName);
		try {
			fileDto.setResource(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			throw new FileHandlingException(ErrorMessage.FILE_RESOLVING_ERROR);
		}
		fileDto.setStatus(FileStatus.UPLOADED);
		fileDto.setContentType(file.getContentType());
		fileDto.setCreatedAt(Instant.now());
		return fileDto;
	}
}
