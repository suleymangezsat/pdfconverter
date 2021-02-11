package com.textkernel.pdfconverter.uploader.api.mapper;

import com.textkernel.pdfconverter.uploader.api.dto.FileTaskDto;
import com.textkernel.pdfconverter.uploader.core.dto.FileTask;

public class FileTaskMapper {

	public static FileTaskDto mapToFileTaskOverview(FileTask file) {
		FileTaskDto response = new FileTaskDto();
		response.setId(file.getId());
		response.setStatus(file.getStatus());
		response.setMessage(file.getMessage());
		response.setOriginalFile(file.getOriginalFile());
		response.setCreatedAt(file.getCreatedAt());
		return response;
	}

	public static FileTaskDto mapToFileTaskDetail(FileTask file) {
		FileTaskDto response = new FileTaskDto();
		response.setId(file.getId());
		response.setConvertingResult(file.getConvertingResult());
		return response;
	}
}
