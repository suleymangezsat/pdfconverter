package com.textkernel.pdfconverter.uploader.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.textkernel.pdfconverter.uploader.api.constant.ErrorMessage;
import com.textkernel.pdfconverter.uploader.api.exception.FileHandlingException;
import com.textkernel.pdfconverter.uploader.core.constant.Status;
import com.textkernel.pdfconverter.uploader.core.dto.FileTask;
import com.textkernel.pdfconverter.uploader.core.dto.OriginalFile;
import com.textkernel.pdfconverter.uploader.core.service.FileStorageService;
import com.textkernel.pdfconverter.uploader.core.service.ProducerService;

class FileUploadServiceTest {
	private static final String ID = "123";
	private static final Status STATUS = Status.INIT;
	private static final byte[] RESOURCE = new byte[0];
	@Mock
	private FileStorageService fileStorageService;
	@Mock
	private ProducerService producerService;

	private FileUploadService fileUploadService;

	private AutoCloseable closeable;

	@BeforeEach
	void setup() {
		closeable = MockitoAnnotations.openMocks(this);
		fileUploadService = new FileUploadService(fileStorageService, producerService);
	}

	@AfterEach
	void closeService() throws Exception {
		closeable.close();
	}

	@Test
	void listAll_Success() {
		FileTask fileTask = mockFileTask();
		when(fileStorageService.getAll()).thenReturn(List.of(fileTask));
		List<FileTask> response = fileUploadService.listAll();

		verify(fileStorageService, Mockito.times(1)).getAll();
		assertEquals(1, response.size());
		assertEquals(ID, response.get(0).getId());
		assertEquals(STATUS, response.get(0).getStatus());
	}

	@Test
	void upload_Success() {
		MockMultipartFile file = new MockMultipartFile("file", "filename.pdf", MediaType.APPLICATION_PDF_VALUE, "some pdf".getBytes());

		FileTask fileTask = mockFileTask();
		when(fileStorageService.create(any(), eq(Status.INIT))).thenReturn(List.of(fileTask));
		doNothing().when(producerService).sendFileToConvertingQueue(anyString(), any(), anyString());
		fileUploadService.upload(List.of(file));

		verify(producerService, Mockito.times(1)).sendFileToConvertingQueue(eq(ID), eq(RESOURCE), eq(MediaType.APPLICATION_PDF_VALUE));
	}

	@Test
	void upload_FileBlankNameError() {
		MultipartFile file = mock(MultipartFile.class);
		when(file.getOriginalFilename()).thenReturn(null);
		FileHandlingException exception = assertThrows(FileHandlingException.class,
				() -> fileUploadService.upload(List.of(file)));
		assertEquals(ErrorMessage.FILE_BLANK_NAME_ERROR, exception.getMessage());
	}

	@Test
	void upload_FileResolvingError() throws IOException {
		MultipartFile file = mock(MultipartFile.class);
		when(file.getOriginalFilename()).thenReturn("name");
		when(file.getBytes()).thenThrow(new IOException("invalid file"));
		FileHandlingException exception = assertThrows(FileHandlingException.class,
				() -> fileUploadService.upload(List.of(file)));
		assertEquals(ErrorMessage.FILE_RESOLVING_ERROR, exception.getMessage());
	}

	private FileTask mockFileTask() {
		OriginalFile originalFile = mock(OriginalFile.class);
		when(originalFile.getResource()).thenReturn(RESOURCE);
		when(originalFile.getContentType()).thenReturn(MediaType.APPLICATION_PDF_VALUE);

		FileTask fileTask = mock(FileTask.class);
		when(fileTask.getId()).thenReturn(ID);
		when(fileTask.getStatus()).thenReturn(STATUS);
		when(fileTask.getOriginalFile()).thenReturn(originalFile);
		return fileTask;
	}
}