package com.textkernel.pdfconverter.uploader.core.service;

public interface ProducerService {
	void sendFileToConvert(String id, byte[] resource, String contentType);
}
