package com.textkernel.pdfconverter.converter.core.exception;

import java.util.List;

public class FileParsingException extends FatalException {
	public FileParsingException(String errorCode, List<String> errorMessages) {
		super(String.format("Error code: %s\nError messages: %s", errorCode, String.join(",", errorMessages)));
	}
}
