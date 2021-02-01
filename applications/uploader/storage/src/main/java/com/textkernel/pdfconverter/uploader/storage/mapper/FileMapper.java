package com.textkernel.pdfconverter.uploader.storage.mapper;

import com.textkernel.pdfconverter.uploader.core.dto.File;
import com.textkernel.pdfconverter.uploader.storage.entity.FileEntity;

public class FileMapper {
	public static FileEntity mapToEntity(File file) {
		FileEntity entity = new FileEntity();
		entity.setName(file.getName());
		entity.setResource(file.getResource());
		entity.setStatus(file.getStatus());
		entity.setTextPages(file.getTextPages());
		entity.setErrorMessages(file.getErrorMessages());
		entity.setConvertingStatus(file.getConvertingStatus());
		entity.setContentType(file.getContentType());
		entity.setCreatedAt(file.getCreatedAt());
		return entity;
	}
}
