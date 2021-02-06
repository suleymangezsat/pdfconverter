package com.textkernel.pdfconverter.uploader.core.service;

import java.util.List;
import java.util.Optional;

import com.textkernel.pdfconverter.uploader.core.constant.Status;
import com.textkernel.pdfconverter.uploader.core.dto.ConvertingResult;
import com.textkernel.pdfconverter.uploader.core.dto.FileTask;
import com.textkernel.pdfconverter.uploader.core.dto.OriginalFile;

public interface FileStorageService {
	List<FileTask> create(List<OriginalFile> originalFiles, Status status);

	void update(String id, Status status, ConvertingResult convertingResult, String message);

	List<FileTask> get(List<String> ids);

	List<FileTask> getAll();
}
