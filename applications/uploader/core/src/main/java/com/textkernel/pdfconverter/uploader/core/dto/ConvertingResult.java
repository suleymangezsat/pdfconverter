package com.textkernel.pdfconverter.uploader.core.dto;

import java.util.List;

import com.textkernel.pdfconverter.uploader.core.constant.ConvertingStatus;

public interface ConvertingResult {
	List<String> getTextPages();

	ConvertingStatus getStatus();

	List<String> getErrorMessages();
}
