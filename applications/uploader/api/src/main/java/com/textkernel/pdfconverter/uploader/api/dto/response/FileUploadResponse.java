package com.textkernel.pdfconverter.uploader.api.dto.response;

import java.time.Instant;

import com.textkernel.pdfconverter.uploader.core.constant.Status;
import com.textkernel.pdfconverter.uploader.core.dto.ConvertingResult;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileUploadResponse {
	private String id;

	private Status status;

	private String message;

	private ConvertingResult convertingResult;

	private Instant createdAt;
}
