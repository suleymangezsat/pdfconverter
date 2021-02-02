package com.textkernel.pdfconverter.converter.core.service;

import com.textkernel.pdfconverter.converter.core.dto.Converted;

public interface ProducerService {
	void sendToUpdatingStatusTask(Converted converted);
}
