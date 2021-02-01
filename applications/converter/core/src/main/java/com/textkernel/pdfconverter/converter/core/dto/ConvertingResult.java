package com.textkernel.pdfconverter.converter.core.dto;

import java.util.List;

import com.textkernel.pdfconverter.converter.core.constant.ConvertingStatus;

public interface ConvertingResult {
	List<String> getTextPages();

	ConvertingStatus getStatus();

	List<String> getErrorMessages();
}
