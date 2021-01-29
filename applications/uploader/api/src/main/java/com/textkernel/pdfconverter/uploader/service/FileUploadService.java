package com.textkernel.pdfconverter.uploader.service;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.textkernel.pdfconverter.uploader.core.constant.Error;
import com.textkernel.pdfconverter.uploader.core.dto.FileDto;
import com.textkernel.pdfconverter.uploader.core.constant.FileStatus;
import com.textkernel.pdfconverter.uploader.core.service.FileStorageService;
import com.textkernel.pdfconverter.uploader.exception.FileHandlingException;

@Service
public class FileUploadService {

	private final FileStorageService fileStorageService;

	public FileUploadService(FileStorageService fileStorageService) {
		this.fileStorageService = fileStorageService;
	}

	public List<FileDto> listAll() {
		return fileStorageService.getAll();
	}

	public FileDto upload(MultipartFile file) {
		FileDto fileDto = createFileDto(file);
		return fileStorageService.store(fileDto);
	}

	private FileDto createFileDto(MultipartFile file) {
		String fileName = StringUtils.cleanPath(Optional.ofNullable(file.getOriginalFilename()).orElseThrow(() -> new FileHandlingException(Error.FILE_NAME_ERROR)));
		FileDto fileDto = new FileDto();
		fileDto.setName(fileName);
		try {
			fileDto.setData(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			throw new FileHandlingException(Error.FILE_RESOLVE_ERROR);
		}
		fileDto.setStatus(FileStatus.UPLOADED);
		fileDto.setCreatedAt(Instant.now());
		return fileDto;
	}
}
