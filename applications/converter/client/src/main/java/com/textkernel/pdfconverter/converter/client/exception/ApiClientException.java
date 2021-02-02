package com.textkernel.pdfconverter.converter.client.exception;

import com.textkernel.pdfconverter.converter.core.exception.FatalException;

public class ApiClientException extends FatalException {
	public ApiClientException(String message) {
		super(message);
	}
}