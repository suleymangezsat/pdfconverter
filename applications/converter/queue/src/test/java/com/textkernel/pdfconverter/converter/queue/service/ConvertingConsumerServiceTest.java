package com.textkernel.pdfconverter.converter.queue.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;

import com.textkernel.pdfconverter.converter.core.constant.Status;
import com.textkernel.pdfconverter.converter.core.dto.ConvertingResult;
import com.textkernel.pdfconverter.converter.core.service.ConverterService;
import com.textkernel.pdfconverter.converter.queue.dto.ConvertingPayload;
import com.textkernel.pdfconverter.converter.queue.dto.StatusUpdatingPayload;

class ConvertingConsumerServiceTest {

	@Mock
	private ConverterService converterService;

	private ConvertingConsumerService consumerService;
	private AutoCloseable closeable;

	@BeforeEach
	void setup() {
		closeable = MockitoAnnotations.openMocks(this);
		consumerService = new ConvertingConsumerService(converterService);
	}

	@AfterEach
	void closeService() throws Exception {
		closeable.close();
	}

	@Test
	void receiveFilesToConvert_Success() {
		ConvertingPayload payload = new ConvertingPayload();
		payload.setId("123");
		payload.setContentType(MediaType.APPLICATION_PDF_VALUE);
		payload.setResource("body".getBytes());

		ConvertingResult mock = mock(ConvertingResult.class);
		when(converterService.convert(eq(payload))).thenReturn(mock);

		StatusUpdatingPayload response = consumerService.receiveFilesToConvert(payload);
		assertEquals(payload.getId(), response.getId());
		assertNull(response.getMessage());
		assertEquals(Status.SUCCESS, response.getStatus());
		assertEquals(mock, response.getConvertingResult());
	}
}