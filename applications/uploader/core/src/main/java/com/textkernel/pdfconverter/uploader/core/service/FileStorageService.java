package com.textkernel.pdfconverter.uploader.core.service;

import java.util.List;

import com.textkernel.pdfconverter.uploader.core.dto.FileDto;

public interface FileStorageService {
	FileDto store(FileDto file);

	List<FileDto> getAll();
}
