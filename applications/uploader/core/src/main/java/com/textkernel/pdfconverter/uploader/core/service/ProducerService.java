package com.textkernel.pdfconverter.uploader.core.service;

/**
 * Service that uses RabbitMQ to send messages by using configured exchange and route.
 * It uses exchange and routing key from application.properties
 */
public interface ProducerService {

	/**
	 * Creates a message to RabbitMQ in order to process input file asynchronously
	 *
	 * @param id
	 * 		ID of the document to be sent
	 * @param resource
	 * 		Binary content of the input file
	 * @param contentType
	 * 		Content type of the input file
	 */
	void sendFileToConvertingQueue(String id, byte[] resource, String contentType);
}
