package com.textkernel.pdfconverter.uploader.mapper;

import com.textkernel.pdfconverter.uploader.core.dto.FileDto;
import com.textkernel.pdfconverter.uploader.dto.response.FileUploadResponse;

public class FileUploadMapper {

	public static FileUploadResponse mapToFileUploadResponse(FileDto file){
		FileUploadResponse response = new FileUploadResponse();
		response.setId(file.getId());
		response.setName(file.getName());
		response.setStatus(file.getStatus());
		response.setText(file.getText());
		return response;
	}
}
