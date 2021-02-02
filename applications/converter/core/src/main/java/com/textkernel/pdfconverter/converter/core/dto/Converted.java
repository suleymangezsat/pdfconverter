package com.textkernel.pdfconverter.converter.core.dto;

import com.textkernel.pdfconverter.converter.core.constant.Status;

public interface Converted {
	String getId();
	ConvertingResult getConvertingResult();
	Status getStatus();
	String getMessage();
}
