package com.textkernel.pdfconverter.uploader.core.service;

public interface ProducerService {
	void sendFileToConvertingQueue(String id, byte[] resource, String contentType);
}
