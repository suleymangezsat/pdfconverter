package com.textkernel.pdfconverter.uploader.core.dto;

import java.time.Instant;

import com.textkernel.pdfconverter.uploader.core.constant.FileStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDto {
	private String id;

	private String name;

	private FileStatus status;

	private String text;

	private byte[] data;

	private Instant createdAt;
}
