package com.textkernel.pdfconverter.uploader.queue.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConvertingPayload {
	private String id;
	private byte[] resource;
	private String contentType;
}
