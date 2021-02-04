package com.textkernel.pdfconverter.uploader.queue.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.textkernel.pdfconverter.uploader.core.constant.ConvertingStatus;
import com.textkernel.pdfconverter.uploader.core.constant.Status;
import com.textkernel.pdfconverter.uploader.core.service.FileStorageService;
import com.textkernel.pdfconverter.uploader.queue.dto.ConvertingResultImpl;
import com.textkernel.pdfconverter.uploader.queue.dto.StatusUpdatingPayload;

class StatusUpdatingConsumerServiceTest {
	private static final String ID = "123";
	private static final String MESSAGE = "message";
	private static final Status STATUS = Status.INIT;
	@Mock
	private FileStorageService fileStorageService;

	private StatusUpdatingConsumerService consumerService;
	private AutoCloseable closeable;

	@BeforeEach
	void setup() {
		closeable = MockitoAnnotations.openMocks(this);
		consumerService = new StatusUpdatingConsumerService(fileStorageService);
	}

	@AfterEach
	void closeService() throws Exception {
		closeable.close();
	}

	@Test
	void receiveFilesToUpdateStatus_Success() {
		ConvertingResultImpl convertingResult = new ConvertingResultImpl();
		convertingResult.setStatus(ConvertingStatus.CONVERTED_SUCCESS);
		convertingResult.setTextPages(List.of("page1", "page2"));
		convertingResult.setErrorMessages(null);
		StatusUpdatingPayload payload = new StatusUpdatingPayload();
		payload.setId(ID);
		payload.setStatus(STATUS);
		payload.setMessage(MESSAGE);
		payload.setConvertingResult(convertingResult);

		doNothing().when(fileStorageService).update(anyString(), any(), any(), anyString());
		consumerService.receiveFilesToUpdateStatus(payload);
		verify(fileStorageService, Mockito.times(1)).update(eq(ID), eq(STATUS), eq(convertingResult), eq(MESSAGE));
	}
}