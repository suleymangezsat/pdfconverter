package com.textkernel.pdfconverter.uploader.storage.entity;

import java.time.Instant;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.textkernel.pdfconverter.uploader.core.constant.ConvertingStatus;
import com.textkernel.pdfconverter.uploader.core.constant.FileStatus;
import com.textkernel.pdfconverter.uploader.core.dto.File;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "files")
public class FileEntity implements File {

	public FileEntity(String name, FileStatus status, byte[] resource) {
		this.name = name;
		this.status = status;
		this.resource = resource;
	}

	@Id
	private String id;

	private String name;

	private FileStatus status;

	private ConvertingStatus convertingStatus;

	private List<String> textPages;

	private byte[] resource;

	private String contentType;

	private List<String> errorMessages;

	private Instant createdAt;

}
