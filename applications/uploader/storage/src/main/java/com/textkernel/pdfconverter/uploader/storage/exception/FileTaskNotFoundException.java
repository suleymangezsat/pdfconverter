package com.textkernel.pdfconverter.uploader.storage.exception;

import com.textkernel.pdfconverter.uploader.core.excepiton.ResourceNotFoundException;

/**
 * This exception is thrown when there is no task found for requested ID
 */
public class FileTaskNotFoundException extends ResourceNotFoundException {
	public FileTaskNotFoundException(String id) {
		super(String.format("File task not found for id: %s", id));
	}
}
