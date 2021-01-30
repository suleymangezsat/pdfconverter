package com.textkernel.pdfconverter.converter.core.dto;

public interface FileMessage {
	String getId();

	byte[] getResource();

	String getContentType();
}
