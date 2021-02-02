package com.textkernel.pdfconverter.uploader.storage.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.textkernel.pdfconverter.uploader.core.constant.Status;
import com.textkernel.pdfconverter.uploader.core.dto.ConvertingResult;
import com.textkernel.pdfconverter.uploader.core.dto.FileTask;
import com.textkernel.pdfconverter.uploader.core.dto.OriginalFile;
import com.textkernel.pdfconverter.uploader.core.service.FileStorageService;
import com.textkernel.pdfconverter.uploader.storage.entity.FileTaskEntity;
import com.textkernel.pdfconverter.uploader.storage.exception.FileTaskNotFoundException;
import com.textkernel.pdfconverter.uploader.storage.repository.FileRepository;

@Service
public class FileStorageServiceImpl implements FileStorageService {
	private final FileRepository fileRepository;

	public FileStorageServiceImpl(FileRepository fileRepository) {
		this.fileRepository = fileRepository;
	}

	@Override
	public FileTask create(OriginalFile originalFile, Status status) {
		FileTaskEntity entity = new FileTaskEntity();
		entity.setOriginalFile(originalFile);
		entity.setStatus(status);
		entity.setCreatedAt(Instant.now());
		return fileRepository.save(entity);
	}

	@Override
	public void update(String id, Status status, ConvertingResult convertingResult, String message) {
		FileTaskEntity entity = fileRepository.findById(id).orElseThrow(() -> new FileTaskNotFoundException(id));
		entity.setStatus(status);
		entity.setConvertingResult(convertingResult);
		entity.setMessage(message);
		fileRepository.save(entity);
	}

	@Override
	public List<FileTask> getAll() {
		return fileRepository.findAll().stream()
				.map(fileEntity -> (FileTask) fileEntity)
				.collect(Collectors.toList());
	}
}
