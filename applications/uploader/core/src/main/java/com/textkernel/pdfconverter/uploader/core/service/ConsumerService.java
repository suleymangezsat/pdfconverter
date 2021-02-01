package com.textkernel.pdfconverter.uploader.core.service;

import com.textkernel.pdfconverter.uploader.core.dto.ConvertedFile;

public interface ConsumerService {
	void updateFileStatus(ConvertedFile convertedFile);
}
