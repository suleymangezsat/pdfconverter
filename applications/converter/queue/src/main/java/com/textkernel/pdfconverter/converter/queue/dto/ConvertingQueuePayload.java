package com.textkernel.pdfconverter.converter.queue.dto;

import com.textkernel.pdfconverter.converter.core.dto.OriginalFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConvertingQueuePayload implements OriginalFile {
	private String id;
	private byte[] resource;
	private String contentType;
}
