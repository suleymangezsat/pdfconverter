package com.textkernel.pdfconverter.converter.core.service;


import com.textkernel.pdfconverter.converter.core.dto.Convertable;
import com.textkernel.pdfconverter.converter.core.dto.ConvertingResult;


/**
 * Interface that defines format of how to convert pdf files to plain text
 */
public interface ConverterService {

	/**
	 * Converts requested PDF file to plain text
	 *
	 * @param file
	 * 		input file to be sent to OCR API
	 * @return OCR service response that contains plain text
	 */
	ConvertingResult convert(Convertable file);
}
