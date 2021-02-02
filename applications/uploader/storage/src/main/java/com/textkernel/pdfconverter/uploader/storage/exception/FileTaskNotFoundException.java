package com.textkernel.pdfconverter.uploader.storage.exception;

import com.textkernel.pdfconverter.uploader.core.excepiton.ResourceNotFoundException;

public class FileTaskNotFoundException extends ResourceNotFoundException {
	public FileTaskNotFoundException(String id) {
		super(String.format("File task not found for id: %s", id));
	}
}
