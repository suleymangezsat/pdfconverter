package com.textkernel.pdfconverter.uploader.api.exception;

/**
 * This exception is throws when file validation fails
 */
public class FileValidationException extends RuntimeException {
	public FileValidationException(String message) {
		super(message);
	}
}
