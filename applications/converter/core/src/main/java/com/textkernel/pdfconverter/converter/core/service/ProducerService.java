package com.textkernel.pdfconverter.converter.core.service;

import com.textkernel.pdfconverter.converter.core.dto.ConvertedFile;

public interface ProducerService {
	void sendToUpdateStatus(ConvertedFile convertedFile);
}
