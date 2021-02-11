package com.textkernel.pdfconverter.uploader.core.dto;

public interface OriginalFile {
	String getName();

	byte[] getResource();

	Long getSize();

	String getContentType();
}

