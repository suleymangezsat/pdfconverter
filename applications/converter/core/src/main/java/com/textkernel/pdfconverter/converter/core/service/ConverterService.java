package com.textkernel.pdfconverter.converter.core.service;


import com.textkernel.pdfconverter.converter.core.dto.FileMessage;

public interface ConverterService {
	String getPlainText(FileMessage file);
}
