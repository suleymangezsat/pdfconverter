package com.textkernel.pdfconverter.converter.core.dto;

public interface Convertable {
	String getId();

	byte[] getResource();

	String getContentType();
}
