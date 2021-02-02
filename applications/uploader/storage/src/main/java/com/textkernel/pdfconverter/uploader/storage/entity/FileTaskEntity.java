package com.textkernel.pdfconverter.uploader.storage.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.textkernel.pdfconverter.uploader.core.constant.Status;
import com.textkernel.pdfconverter.uploader.core.dto.ConvertingResult;
import com.textkernel.pdfconverter.uploader.core.dto.FileTask;
import com.textkernel.pdfconverter.uploader.core.dto.OriginalFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "fileTasks")
public class FileTaskEntity implements FileTask {

	@Id
	private String id;

	private Status status;

	private String message;

	private OriginalFile originalFile;

	private ConvertingResult convertingResult;

	private Instant createdAt;
}
