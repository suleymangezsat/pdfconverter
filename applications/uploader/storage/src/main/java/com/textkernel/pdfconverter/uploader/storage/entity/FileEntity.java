package com.textkernel.pdfconverter.uploader.storage.entity;

import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.textkernel.pdfconverter.uploader.core.constant.FileStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "files")
public class FileEntity {

	public FileEntity(String name, FileStatus status, byte[] data) {
		this.name = name;
		this.status = status;
		this.data = data;
	}

	@Id
	private String id;

	private String name;

	private FileStatus status;

	private String text;

	private byte[] data;

	private Instant createdAt;

}
