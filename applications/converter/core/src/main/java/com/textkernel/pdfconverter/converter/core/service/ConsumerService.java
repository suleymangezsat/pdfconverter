package com.textkernel.pdfconverter.converter.core.service;

import com.textkernel.pdfconverter.converter.core.dto.OriginalFile;

public interface ConsumerService {
	void sendFileToConvert(OriginalFile file);
}
