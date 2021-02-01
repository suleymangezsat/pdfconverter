package com.textkernel.pdfconverter.converter.core.service;


import com.textkernel.pdfconverter.converter.core.dto.ConvertingResult;
import com.textkernel.pdfconverter.converter.core.dto.OriginalFile;

public interface ConverterService {
	ConvertingResult convert(OriginalFile file);
}
