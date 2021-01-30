package com.textkernel.pdfconverter.uploader.storage.mapper;

import com.textkernel.pdfconverter.uploader.core.dto.FileDto;
import com.textkernel.pdfconverter.uploader.storage.entity.FileEntity;

public class FileMapper {
	public static FileDto mapToDto(FileEntity fileEntity) {
		FileDto dto = new FileDto();
		dto.setId(fileEntity.getId());
		dto.setName(fileEntity.getName());
		dto.setText(fileEntity.getText());
		dto.setResource(fileEntity.getResource());
		dto.setContentType(fileEntity.getContentType());
		dto.setStatus(fileEntity.getStatus());
		dto.setCreatedAt(fileEntity.getCreatedAt());
		return dto;
	}

	public static FileEntity mapToEntity(FileDto file) {
		FileEntity entity = new FileEntity();
		entity.setName(file.getName());
		entity.setResource(file.getResource());
		entity.setStatus(file.getStatus());
		entity.setText(file.getText());
		entity.setContentType(file.getContentType());
		entity.setCreatedAt(file.getCreatedAt());
		return entity;
	}
}
