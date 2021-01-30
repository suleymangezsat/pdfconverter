package com.textkernel.pdfconverter.uploader.queue.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileMessage {
	private String id;
	private byte[] resource;
	private String contentType;
}
