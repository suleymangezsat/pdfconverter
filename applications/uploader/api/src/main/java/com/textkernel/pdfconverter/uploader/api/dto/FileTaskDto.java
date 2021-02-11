package com.textkernel.pdfconverter.uploader.api.dto;

import java.time.Instant;

import com.textkernel.pdfconverter.uploader.core.constant.Status;
import com.textkernel.pdfconverter.uploader.core.dto.ConvertingResult;
import com.textkernel.pdfconverter.uploader.core.dto.FileTask;
import com.textkernel.pdfconverter.uploader.core.dto.OriginalFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileTaskDto implements FileTask {
	private String id;

	private Status status;

	private String message;

	private OriginalFile originalFile;

	private ConvertingResult convertingResult;

	private Instant createdAt;
}
