package com.textkernel.pdfconverter.uploader.api.exception;

import com.textkernel.pdfconverter.uploader.core.constant.ErrorMessage;

public class FileHandlingException extends RuntimeException {
	public FileHandlingException(ErrorMessage error) {
		super(error.toString());
	}
}

