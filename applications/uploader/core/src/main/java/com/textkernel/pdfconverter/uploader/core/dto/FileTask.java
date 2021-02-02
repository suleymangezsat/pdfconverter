package com.textkernel.pdfconverter.uploader.core.dto;

import java.time.Instant;

import com.textkernel.pdfconverter.uploader.core.constant.Status;

public interface FileTask {
	String getId();

	Status getStatus();

	String getMessage();

	ConvertingResult getConvertingResult();

	OriginalFile getOriginalFile();

	Instant getCreatedAt();
}
