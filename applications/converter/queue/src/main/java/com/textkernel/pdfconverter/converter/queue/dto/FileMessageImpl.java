package com.textkernel.pdfconverter.converter.queue.dto;

import com.textkernel.pdfconverter.converter.core.dto.FileMessage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileMessageImpl implements FileMessage {
	private String id;
	private byte[] resource;
	private String contentType;
}
