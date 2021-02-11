package com.textkernel.pdfconverter.converter.client.exception;

import com.textkernel.pdfconverter.converter.core.exception.FatalException;

/**
 * This exception is thrown when there is a client error with sending request to remote services
 */
public class ApiClientException extends FatalException {
	public ApiClientException(String message) {
		super(message);
	}
}