package com.textkernel.pdfconverter.uploader.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.textkernel.pdfconverter.uploader.api.configuration.MessageConverterConfiguration;
import com.textkernel.pdfconverter.uploader.api.constant.ErrorMessage;
import com.textkernel.pdfconverter.uploader.api.dto.FileTaskDto;
import com.textkernel.pdfconverter.uploader.api.dto.FileValidationResult;
import com.textkernel.pdfconverter.uploader.api.dto.response.GetFileTasksResponse;
import com.textkernel.pdfconverter.uploader.api.dto.response.UploadFilesResponse;
import com.textkernel.pdfconverter.uploader.api.mapper.FileErrorMapper;
import com.textkernel.pdfconverter.uploader.api.mapper.FileTaskMapper;
import com.textkernel.pdfconverter.uploader.api.service.FileUploadService;
import com.textkernel.pdfconverter.uploader.api.validation.FileValidator;
import com.textkernel.pdfconverter.uploader.core.constant.Status;
import com.textkernel.pdfconverter.uploader.core.dto.FileTask;
import com.textkernel.pdfconverter.uploader.core.properties.FileProperties;

@SpringBootTest(classes = {FileController.class, FileValidator.class, MessageConverterConfiguration.class})
@AutoConfigureMockMvc
@AutoConfigureWebMvc
class FileControllerTest {

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@MockBean
	private FileUploadService fileUploadService;

	@MockBean
	private FileProperties fileProperties;

	private AutoCloseable closeable;

	@BeforeEach
	void setup() {
		closeable = MockitoAnnotations.openMocks(this);

		when(fileProperties.getMaxFileCount()).thenReturn(3);
		when(fileProperties.getMaxFileSize()).thenReturn(2_048_000L);
	}

	@AfterEach
	void closeService() throws Exception {
		closeable.close();
	}

	@Test
	void listAllFileTasks_List() throws Exception {
		Instant createdAt = Instant.now();
		FileTask initTask = mockFileTask(createdAt, "111", Status.INIT, null);
		FileTask successTask = mockFileTask(createdAt, "222", Status.SUCCESS, null);
		FileTask failTask = mockFileTask(createdAt, "333", Status.FAILED, "ooppss");


		when(fileUploadService.listAll()).thenReturn(List.of(initTask, successTask, failTask));

		GetFileTasksResponse expected = generateGetFileTasksResponse(initTask, successTask, failTask);
		mockMvc.perform(get("/file/all")
				.contentType(APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().json(mapper.writeValueAsString(expected)));
	}

	@Test
	void listAllFileTasks_Empty() throws Exception {
		when(fileUploadService.listAll()).thenReturn(List.of());

		mockMvc.perform(get("/file/all")
				.contentType(APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().json(mapper.writeValueAsString(new GetFileTasksResponse(List.of()))));
	}

	@Test
	void listFileTasks_List() throws Exception {
		Instant createdAt = Instant.now();
		FileTask initTask = mockFileTask(createdAt, "111", Status.INIT, null);
		FileTask successTask = mockFileTask(createdAt, "222", Status.SUCCESS, null);


		when(fileUploadService.list(eq(List.of("111", "222", "333")))).thenReturn(List.of(initTask, successTask));

		GetFileTasksResponse expected = generateGetFileTasksResponse(initTask, successTask);
		mockMvc.perform(get("/file")
				.queryParam("ids", "111", "222", "333")
				.contentType(APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().json(mapper.writeValueAsString(expected)));
	}

	@Test
	void listFileTasks_Empty() throws Exception {
		when(fileUploadService.list(any())).thenReturn(List.of());

		mockMvc.perform(get("/file")
				.queryParam("ids", "111", "222", "333")
				.contentType(APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().json(mapper.writeValueAsString(new GetFileTasksResponse(List.of()))));
	}

	@Test
	void uploadFiles_SuccessAndError() throws Exception {
		MockMultipartFile success = new MockMultipartFile("file", "filename.pdf", MediaType.APPLICATION_PDF_VALUE, "some pdf".getBytes());
		MockMultipartFile error = new MockMultipartFile("file", "filename.pdf", MediaType.TEXT_PLAIN_VALUE, "some pdf".getBytes());

		Instant createdAt = Instant.now();
		FileTask fileTask = mockFileTask(createdAt, "111", Status.INIT, null);

		when(fileUploadService.upload(eq(List.of(success)))).thenReturn(List.of(fileTask));

		UploadFilesResponse expected = new UploadFilesResponse();
		expected.setData(List.of(FileTaskMapper.mapToFileTaskOverview(fileTask)));
		expected.setErrors(List.of(FileErrorMapper.mapToFileError(new FileValidationResult(error, List.of(ErrorMessage.INVALID_CONTENT_TYPE)))));

		mockMvc.perform(multipart("/file")
				.file(success)
				.file(error))
				.andExpect(status().isOk())
				.andExpect(content().string(mapper.writeValueAsString(expected)));
	}

	private GetFileTasksResponse generateGetFileTasksResponse(FileTask... tasks) {
		List<FileTaskDto> dtos = Arrays.stream(tasks)
				.map(FileTaskMapper::mapToFileTaskDetail)
				.collect(Collectors.toList());

		return new GetFileTasksResponse(dtos);
	}

	private FileTask mockFileTask(Instant createdAt, String id, Status status, String message) {
		FileTask fileTask = Mockito.mock(FileTask.class);
		when(fileTask.getId()).thenReturn(id);
		when(fileTask.getCreatedAt()).thenReturn(createdAt);
		when(fileTask.getStatus()).thenReturn(status);
		when(fileTask.getMessage()).thenReturn(message);

		return fileTask;
	}
}
