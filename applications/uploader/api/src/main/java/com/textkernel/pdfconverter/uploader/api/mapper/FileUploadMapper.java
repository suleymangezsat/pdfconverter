package com.textkernel.pdfconverter.uploader.api.mapper;

import com.textkernel.pdfconverter.uploader.api.dto.response.FileUploadResponse;
import com.textkernel.pdfconverter.uploader.core.dto.FileTask;

public class FileUploadMapper {

	public static FileUploadResponse mapToFileUploadResponse(FileTask file) {
		FileUploadResponse response = new FileUploadResponse();
		response.setId(file.getId());
		response.setStatus(file.getStatus());
		response.setMessage(file.getMessage());
		response.setConvertingResult(file.getConvertingResult());
		response.setCreatedAt(file.getCreatedAt());
		return response;
	}
}
