package com.textkernel.pdfconverter.converter.queue.dto;

import com.textkernel.pdfconverter.converter.core.dto.Convertable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConvertingPayload implements Convertable {
	private String id;
	private byte[] resource;
	private String contentType;
}
