package com.textkernel.pdfconverter.converter.queue.errorhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.messaging.MessageHeaders;

import com.textkernel.pdfconverter.converter.core.constant.Status;
import com.textkernel.pdfconverter.converter.core.exception.FatalException;
import com.textkernel.pdfconverter.converter.core.properties.RabbitmqProperties;
import com.textkernel.pdfconverter.converter.core.service.ProducerService;
import com.textkernel.pdfconverter.converter.queue.dto.ConvertingPayload;
import com.textkernel.pdfconverter.converter.queue.dto.StatusUpdatingPayload;

class ConvertingTaskListenerErrorHandlerTest {

	@Mock
	private RabbitmqProperties rabbitmqProperties;
	@Mock
	private ProducerService producerService;
	@Mock
	org.springframework.messaging.Message<ConvertingPayload> message;
	@Mock
	private ConvertingPayload payload;
	@Mock
	private ListenerExecutionFailedException failedException;

	private ConvertingTaskListenerErrorHandler errorHandler;
	private AutoCloseable closeable;

	@BeforeEach
	void setup() {
		closeable = MockitoAnnotations.openMocks(this);
		errorHandler = new ConvertingTaskListenerErrorHandler(rabbitmqProperties, producerService);
		when(rabbitmqProperties.getConvertingMaxRetryCount()).thenReturn(2);

		when(message.getPayload()).thenReturn(payload);
		when(message.getHeaders()).thenReturn(new MessageHeaders(Map.of()));
	}

	@AfterEach
	void closeService() throws Exception {
		closeable.close();
	}

	@Test
	void handleError_FatalException() {
		when(failedException.getCause()).thenReturn(new FatalException("message"));
		when(payload.getId()).thenReturn("123");

		Object response = errorHandler.handleError(null, message, failedException);
		assertTrue(response instanceof StatusUpdatingPayload);
		StatusUpdatingPayload actual = (StatusUpdatingPayload) response;
		assertEquals("123", actual.getId());
		assertEquals("message", actual.getMessage());
		assertEquals(Status.FAILED, actual.getStatus());
	}

	@Test
	void handleError_RetriesExceeded() {
		when(failedException.getCause()).thenReturn(new Exception("non-fatal"));
		when(payload.getId()).thenReturn("123");

		Map<String, Long> map = Map.of("count", 3L);
		when(message.getHeaders()).thenReturn(new MessageHeaders(Map.of("x-death", List.of(map))));

		Object response = errorHandler.handleError(null, message, failedException);
		assertTrue(response instanceof StatusUpdatingPayload);
		StatusUpdatingPayload actual = (StatusUpdatingPayload) response;
		assertEquals("123", actual.getId());
		assertEquals("non-fatal", actual.getMessage());
		assertEquals(Status.FAILED, actual.getStatus());
	}

	@Test
	void handleError_NonFatal() {
		when(failedException.getCause()).thenReturn(new Exception("non-fatal"));
		when(payload.getId()).thenReturn("123");

		assertThrows(Exception.class,
				() -> errorHandler.handleError(null, message, failedException));

		verify(producerService, Mockito.times(1))
				.sendToUpdatingStatusTask(any(StatusUpdatingPayload.class));
	}
}