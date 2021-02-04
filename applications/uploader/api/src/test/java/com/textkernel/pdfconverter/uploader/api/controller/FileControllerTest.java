package com.textkernel.pdfconverter.uploader.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.textkernel.pdfconverter.uploader.api.configuration.MessageConverterConfiguration;
import com.textkernel.pdfconverter.uploader.api.constant.ErrorMessage;
import com.textkernel.pdfconverter.uploader.api.dto.response.FileUploadResponse;
import com.textkernel.pdfconverter.uploader.api.exception.FileHandlingException;
import com.textkernel.pdfconverter.uploader.api.service.FileUploadService;
import com.textkernel.pdfconverter.uploader.core.constant.ConvertingStatus;
import com.textkernel.pdfconverter.uploader.core.constant.Status;
import com.textkernel.pdfconverter.uploader.core.dto.ConvertingResult;
import com.textkernel.pdfconverter.uploader.core.dto.FileTask;

@SpringBootTest(classes = {FileController.class, MessageConverterConfiguration.class})
@AutoConfigureMockMvc
@AutoConfigureWebMvc
class FileControllerTest {

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@MockBean
	private FileUploadService fileUploadService;

	private AutoCloseable closeable;

	@BeforeEach
	void setup() {
		closeable = MockitoAnnotations.openMocks(this);
	}

	@AfterEach
	void closeService() throws Exception {
		closeable.close();
	}

	@Test
	void listUploadedFiles_List() throws Exception {
		Instant createdAt = Instant.now();
		FileTask initTask = mockFileTask(createdAt, "111", Status.INIT, null, null);
		FileTask successTask = mockFileTask(createdAt, "222", Status.SUCCESS, null, List.of("line1", "line2"));
		FileTask failTask = mockFileTask(createdAt, "333", Status.FAILED, "ooppss", null);

		FileUploadResponse initResponse = generateFileUploadResponse(createdAt, "111", Status.INIT, null, null);
		FileUploadResponse successResponse = generateFileUploadResponse(createdAt, "222", Status.SUCCESS, null, List.of("line1", "line2"));
		FileUploadResponse failResponse = generateFileUploadResponse(createdAt, "333", Status.FAILED, "ooppss", null);

		when(fileUploadService.listAll()).thenReturn(List.of(initTask, successTask, failTask));

		mockMvc.perform(get("/file")
				.contentType(APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().json(mapper.writeValueAsString(List.of(initResponse, successResponse, failResponse))));
	}

	@Test
	void listUploadedFiles_Empty() throws Exception {
		when(fileUploadService.listAll()).thenReturn(List.of());

		mockMvc.perform(get("/file")
				.contentType(APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().string("[]"));
	}

	@Test
	void uploadFile_Success() throws Exception {
		MockMultipartFile file = new MockMultipartFile("file", "filename.pdf", MediaType.APPLICATION_PDF_VALUE, "some pdf".getBytes());

		Instant createdAt = Instant.now();
		FileTask fileTask = mockFileTask(createdAt, "111", Status.INIT, null, null);
		when(fileUploadService.upload(eq(file))).thenReturn(fileTask);

		FileUploadResponse expected = generateFileUploadResponse(createdAt, "111", Status.INIT, null, null);

		mockMvc.perform(multipart("/file")
				.file(file))
				.andExpect(status().isOk())
				.andExpect(content().string(mapper.writeValueAsString(expected)));
	}

	@Test
	void uploadFile_FileHandlingException() {
		MockMultipartFile file = new MockMultipartFile("file", "filename.pdf", MediaType.APPLICATION_PDF_VALUE, "some pdf".getBytes());

		when(fileUploadService.upload(eq(file))).thenThrow(new FileHandlingException(ErrorMessage.FILE_BLANK_NAME_ERROR));

		NestedServletException exception = assertThrows(NestedServletException.class,
				() -> mockMvc.perform(multipart("/file")
						.file(file))
						.andExpect(status().is4xxClientError()));
		assertTrue(exception.getCause() instanceof FileHandlingException);
		assertEquals(ErrorMessage.FILE_BLANK_NAME_ERROR, exception.getCause().getMessage());

	}

	private FileUploadResponse generateFileUploadResponse(Instant createdAt, String id, Status status, String message, List<String> resultPages) {
		ConvertingResult convertingResult = generateConvertingResult(resultPages);

		FileUploadResponse response = new FileUploadResponse();
		response.setCreatedAt(createdAt);
		response.setConvertingResult(convertingResult);
		response.setMessage(message);
		response.setId(id);
		response.setStatus(status);
		return response;
	}

	private FileTask mockFileTask(Instant createdAt, String id, Status status, String message, List<String> resultPages) {
		ConvertingResult convertingResult = generateConvertingResult(resultPages);

		FileTask fileTask = Mockito.mock(FileTask.class);
		when(fileTask.getId()).thenReturn(id);
		when(fileTask.getCreatedAt()).thenReturn(createdAt);
		when(fileTask.getStatus()).thenReturn(status);
		when(fileTask.getMessage()).thenReturn(message);
		when(fileTask.getConvertingResult()).thenReturn(convertingResult);

		return fileTask;
	}

	private ConvertingResult generateConvertingResult(List<String> resultPages) {
		return new ConvertingResult() {
			@Override
			public List<String> getTextPages() {
				return resultPages;
			}

			@Override
			public ConvertingStatus getStatus() {
				return null;
			}

			@Override
			public List<String> getErrorMessages() {
				return null;
			}
		};
	}
}