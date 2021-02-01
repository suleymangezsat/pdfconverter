package com.textkernel.pdfconverter.uploader.storage.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.textkernel.pdfconverter.uploader.core.dto.File;
import com.textkernel.pdfconverter.uploader.core.service.FileStorageService;
import com.textkernel.pdfconverter.uploader.storage.entity.FileEntity;
import com.textkernel.pdfconverter.uploader.storage.mapper.FileMapper;
import com.textkernel.pdfconverter.uploader.storage.repository.FileRepository;

@Service
public class FileStorageServiceImpl implements FileStorageService {
	private final FileRepository fileRepository;

	public FileStorageServiceImpl(FileRepository fileRepository) {
		this.fileRepository = fileRepository;
	}

	@Override
	public File store(File file) {
		return fileRepository.save(FileMapper.mapToEntity(file));
	}

	@Override
	public Optional<File> get(String id) {
		return fileRepository.findById(id).map(File.class::cast);
	}

	@Override
	public List<File> getAll() {
		return fileRepository.findAll().stream().map(fileEntity -> (File) fileEntity).collect(Collectors.toList());
	}
}
