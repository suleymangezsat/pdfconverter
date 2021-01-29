package com.textkernel.pdfconverter.uploader.storage.mapper;

import com.textkernel.pdfconverter.uploader.core.dto.FileDto;
import com.textkernel.pdfconverter.uploader.storage.entity.FileEntity;

public class FileMapper {
	public static FileDto mapToDto(FileEntity fileEntity) {
		FileDto dto = new FileDto();
		dto.setId(fileEntity.getId());
		dto.setName(fileEntity.getName());
		dto.setText(fileEntity.getText());
		dto.setData(fileEntity.getData());
		dto.setStatus(fileEntity.getStatus());
		dto.setCreatedAt(fileEntity.getCreatedAt());
		return dto;
	}

	public static FileEntity mapToEntity(FileDto file) {
		FileEntity entity = new FileEntity();
		entity.setName(file.getName());
		entity.setData(file.getData());
		entity.setStatus(file.getStatus());
		entity.setText(file.getText());
		entity.setCreatedAt(file.getCreatedAt());
		return entity;
	}
}
