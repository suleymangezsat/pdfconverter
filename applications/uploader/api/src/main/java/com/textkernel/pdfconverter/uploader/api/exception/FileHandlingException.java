package com.textkernel.pdfconverter.uploader.api.exception;

/**
 * This exception is throws when there is error when resolving file
 */
public class FileHandlingException extends RuntimeException {
	public FileHandlingException(String message) {
		super(message);
	}
}

