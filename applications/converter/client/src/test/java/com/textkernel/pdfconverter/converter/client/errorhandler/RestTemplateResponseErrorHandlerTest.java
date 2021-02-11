package com.textkernel.pdfconverter.converter.client.errorhandler;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;

import com.textkernel.pdfconverter.converter.client.exception.ApiClientException;

class RestTemplateResponseErrorHandlerTest {

	@Mock
	private ClientHttpResponse clientHttpResponse;

	private RestTemplateResponseErrorHandler errorHandler;
	private AutoCloseable closeable;

	static Stream<Arguments> listForHasError() {
		return Stream.of(
				Arguments.of(HttpStatus.BAD_REQUEST, true),
				Arguments.of(HttpStatus.INTERNAL_SERVER_ERROR, true),
				Arguments.of(HttpStatus.OK, false)
		);
	}

	@BeforeEach
	void setup() {
		closeable = MockitoAnnotations.openMocks(this);
		errorHandler = new RestTemplateResponseErrorHandler();
	}

	@AfterEach
	void closeService() throws Exception {
		closeable.close();
	}

	@ParameterizedTest(name = "{index} status= {0}, hasError= {1}")
	@MethodSource("listForHasError")
	void hasError(HttpStatus status, boolean hasError) throws IOException {
		when(clientHttpResponse.getStatusCode()).thenReturn(status);
		assertEquals(hasError, errorHandler.hasError(clientHttpResponse));
	}

	@Test
	void handleError_Success() throws IOException {
		when(clientHttpResponse.getStatusCode()).thenReturn(HttpStatus.OK);
		assertDoesNotThrow(() -> errorHandler.handleError(clientHttpResponse));
	}

	@Test
	void handleError_ClientError() throws IOException {
		when(clientHttpResponse.getStatusCode()).thenReturn(HttpStatus.BAD_REQUEST);
		when(clientHttpResponse.getStatusText()).thenReturn("error message");

		ApiClientException exception = assertThrows(ApiClientException.class,
				() -> errorHandler.handleError(clientHttpResponse));
		assertEquals("error message", exception.getMessage());
	}

	@Test
	void handleError_ServerError() throws IOException {
		when(clientHttpResponse.getStatusCode()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);
		when(clientHttpResponse.getStatusCode()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);

		HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
				() -> errorHandler.handleError(clientHttpResponse));
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
	}

}