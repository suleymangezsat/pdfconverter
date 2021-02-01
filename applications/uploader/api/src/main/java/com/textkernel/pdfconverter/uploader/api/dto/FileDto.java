package com.textkernel.pdfconverter.uploader.api.dto;

import java.time.Instant;
import java.util.List;

import com.textkernel.pdfconverter.uploader.core.constant.ConvertingStatus;
import com.textkernel.pdfconverter.uploader.core.constant.FileStatus;
import com.textkernel.pdfconverter.uploader.core.dto.File;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDto implements File {
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
