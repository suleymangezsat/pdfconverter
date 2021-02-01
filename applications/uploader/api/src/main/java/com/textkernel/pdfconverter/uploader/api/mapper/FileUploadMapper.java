package com.textkernel.pdfconverter.uploader.api.mapper;

import com.textkernel.pdfconverter.uploader.api.dto.response.FileUploadResponse;
import com.textkernel.pdfconverter.uploader.core.dto.File;

public class FileUploadMapper {

	public static FileUploadResponse mapToFileUploadResponse(File file){
		FileUploadResponse response = new FileUploadResponse();
		response.setId(file.getId());
		response.setName(file.getName());
		response.setStatus(file.getStatus());
		response.setTextPages(file.getTextPages());
		return response;
	}
}
