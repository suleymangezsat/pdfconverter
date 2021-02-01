package com.textkernel.pdfconverter.uploader.api.exception;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@ControllerAdvice
public class DefaultExceptionHandler {
	@ExceptionHandler(IOException.class)
	public ResponseEntity<ErrorResponse> handleIOException(IOException e) {
		return handleError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(FileHandlingException.class)
	public ResponseEntity<ErrorResponse> handleFileHandlingException(FileHandlingException e) {
		return handleError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({Exception.class})
	public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
		return handleError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity<ErrorResponse> handleError(String message, HttpStatus httpStatus) {
		return new ResponseEntity<>(new ErrorResponse(message, httpStatus.value()), httpStatus);
	}

	@Getter
	@AllArgsConstructor
	public static class ErrorResponse {
		private String message;
		private int status;
	}
}
