package com.textkernel.pdfconverter.uploader.api.dto.response;

import java.time.Instant;

import com.textkernel.pdfconverter.uploader.core.constant.FileStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileUploadResponse {
	private String id;

	private String name;

	private FileStatus status;

	private String text;
}
