package com.textkernel.pdfconverter.uploader.api.exception;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.textkernel.pdfconverter.uploader.api.configuration.MessageConverterConfiguration;
import com.textkernel.pdfconverter.uploader.core.excepiton.ResourceNotFoundException;

@SpringBootTest(classes = {MessageConverterConfiguration.class})
class DefaultExceptionHandlerTest {

	private DefaultExceptionHandler handler;

	private AutoCloseable closeable;

	@BeforeEach
	void setup() {
		closeable = MockitoAnnotations.openMocks(this);
		handler = new DefaultExceptionHandler();
	}

	@AfterEach
	void closeService() throws Exception {
		closeable.close();
	}

	@Test
	void handleFileHandlingException_BAD_REQUEST() {
		FileHandlingException exception = new FileHandlingException("file handling error");
		ResponseEntity<DefaultExceptionHandler.ErrorResponse> responseEntity = handler.handleFileHandlingException(exception);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getBody().getStatus());
		assertEquals("file handling error", responseEntity.getBody().getMessage());
	}

	@Test
	void handleResourceNotFoundException_NOT_FOUND() {
		ResourceNotFoundException exception = new ResourceNotFoundException("resource not found");
		ResponseEntity<DefaultExceptionHandler.ErrorResponse> responseEntity = handler.handleResourceNotFoundException(exception);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertEquals(HttpStatus.NOT_FOUND.value(), responseEntity.getBody().getStatus());
		assertEquals("resource not found", responseEntity.getBody().getMessage());
	}

	@Test
	void handleIOException_INTERNAL_SERVER_ERROR() {
		IOException exception = new IOException("io exception");
		ResponseEntity<DefaultExceptionHandler.ErrorResponse> responseEntity = handler.handleIOException(exception);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getBody().getStatus());
		assertEquals("io exception", responseEntity.getBody().getMessage());
	}

	@Test
	void handleGenericException_NotFound() {
		Exception exception = new Exception("unexpected error occurred");
		ResponseEntity<DefaultExceptionHandler.ErrorResponse> responseEntity = handler.handleGenericException(exception);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getBody().getStatus());
		assertEquals("unexpected error occurred", responseEntity.getBody().getMessage());
	}
}