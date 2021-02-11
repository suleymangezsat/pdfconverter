package com.textkernel.pdfconverter.uploader.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.textkernel.pdfconverter.uploader.api.mapper.OriginalFileMapper;
import com.textkernel.pdfconverter.uploader.core.constant.Status;
import com.textkernel.pdfconverter.uploader.core.dto.FileTask;
import com.textkernel.pdfconverter.uploader.core.dto.OriginalFile;
import com.textkernel.pdfconverter.uploader.core.service.FileStorageService;
import com.textkernel.pdfconverter.uploader.core.service.ProducerService;

/**
 * Service that is responsible for file operations
 */
@Service
public class FileUploadService {
	private static final Logger logger = LoggerFactory.getLogger(FileUploadService.class);
	private final FileStorageService fileStorageService;
	private final ProducerService fileQueueService;

	public FileUploadService(FileStorageService fileStorageService, ProducerService fileQueueService) {
		this.fileStorageService = fileStorageService;
		this.fileQueueService = fileQueueService;
	}

	/**
	 * @return all tasks from storage service
	 */
	public List<FileTask> listAll() {
		return fileStorageService.getAll();
	}

	/**
	 * @param ids
	 * 		ID of the tasks that will be fetched from database
	 * @return tasks with requested IDs
	 */
	public List<FileTask> list(List<String> ids) {
		return fileStorageService.get(ids);
	}

	/**
	 * @param id
	 * 		ID of the task to be fetched from database
	 * @return task with requested ID
	 */
	public FileTask get(String id) {
		return fileStorageService.get(id);
	}

	/**
	 * Creates a record in database with INIT status and creates a RabbitMQ message to convert file asynchronously
	 *
	 * @param files
	 * 		input files to be converted
	 * @return data about created database records
	 */
	public List<FileTask> upload(List<MultipartFile> files) {
		List<OriginalFile> originalFiles = files.stream()
				.map(OriginalFileMapper::mapToOriginalFile)
				.collect(Collectors.toList());
		List<FileTask> uploadedFiles = fileStorageService.create(originalFiles, Status.INIT);

		logger.info("Stored files successfully");
		uploadedFiles.forEach(uploadedFile ->
				fileQueueService.sendFileToConvertingQueue(uploadedFile.getId(), uploadedFile.getOriginalFile().getResource(), uploadedFile.getOriginalFile().getContentType())
		);
		return uploadedFiles;
	}


}
