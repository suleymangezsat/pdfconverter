package com.textkernel.pdfconverter.converter.core.service;


import com.textkernel.pdfconverter.converter.core.dto.Convertable;
import com.textkernel.pdfconverter.converter.core.dto.ConvertingResult;

public interface ConverterService {
	ConvertingResult convert(Convertable file);
}
