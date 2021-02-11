package com.textkernel.pdfconverter.converter.core.service;

import com.textkernel.pdfconverter.converter.core.dto.Converted;

/**
 * Service that uses RabbitMQ to send messages by using configured exchange and route.
 * It uses exchange and routing key from application.properties
 */
public interface ProducerService {

	/**
	 * Send file convertion upates to statusUpdate queue to be persisted
	 *
	 * @param converted
	 * 		input that contains information about file conversion
	 */
	void sendToUpdatingStatusTask(Converted converted);
}
