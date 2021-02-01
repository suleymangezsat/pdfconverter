package com.textkernel.pdfconverter.uploader.core.service;

import java.util.List;
import java.util.Optional;

import com.textkernel.pdfconverter.uploader.core.dto.File;

public interface FileStorageService {
	File store(File file);

	Optional<File> get(String id);

	List<File> getAll();
}
