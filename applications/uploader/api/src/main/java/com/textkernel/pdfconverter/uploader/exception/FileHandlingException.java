package com.textkernel.pdfconverter.uploader.exception;

import com.textkernel.pdfconverter.uploader.core.constant.Error;

public class FileHandlingException extends RuntimeException {
	public FileHandlingException(Error error) {
		super(error.toString());
	}
}

