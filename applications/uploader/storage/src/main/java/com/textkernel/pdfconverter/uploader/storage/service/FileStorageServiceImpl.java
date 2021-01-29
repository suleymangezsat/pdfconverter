package com.textkernel.pdfconverter.uploader.storage.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.textkernel.pdfconverter.uploader.core.dto.FileDto;
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
	public FileDto store(FileDto file) {
		FileEntity createdEntity = fileRepository.save(FileMapper.mapToEntity(file));
		return FileMapper.mapToDto(createdEntity);
	}

	@Override
	public List<FileDto> getAll() {
		List<FileEntity> fileEntities = fileRepository.findAll();
		return fileEntities.stream().map(FileMapper::mapToDto).collect(Collectors.toList());
	}
}
