package com.textkernel.pdfconverter.uploader.api.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.textkernel.pdfconverter.uploader.api.constant.ErrorMessage;
import com.textkernel.pdfconverter.uploader.api.dto.FileValidationResult;
import com.textkernel.pdfconverter.uploader.api.exception.FileValidationException;
import com.textkernel.pdfconverter.uploader.core.properties.FileProperties;

class FileValidatorTest {

	@Mock
	private FileProperties fileProperties;

	private FileValidator fileValidator;
	private AutoCloseable closeable;

	@BeforeEach
	void setup() {
		closeable = MockitoAnnotations.openMocks(this);
		fileValidator = new FileValidator(fileProperties);
		when(fileProperties.getMaxFileCount()).thenReturn(3);
		when(fileProperties.getMaxFileSize()).thenReturn(2_048_000L);
	}

	@AfterEach
	void closeService() throws Exception {
		closeable.close();
	}

	@Test
	void validate_Success() {
		MockMultipartFile file1 = new MockMultipartFile("file", "filename.pdf", MediaType.APPLICATION_PDF_VALUE, "some pdf".getBytes());
		MockMultipartFile file2 = new MockMultipartFile("file", "filename.pdf", MediaType.APPLICATION_PDF_VALUE, "some pdf".getBytes());

		assertDoesNotThrow(() -> fileValidator.validate(List.of(file1, file2)));
	}

	@Test
	void validate_EmptyList() {
		FileValidationException exception = assertThrows(FileValidationException.class,
				() -> fileValidator.validate(List.of()));
		assertEquals(ErrorMessage.NO_FILE_ERROR, exception.getMessage());
	}

	@Test
	void validate_MaxFileCountExceeded() {
		when(fileProperties.getMaxFileCount()).thenReturn(3);

		MultipartFile mock = generateMock();

		FileValidationException exception = assertThrows(FileValidationException.class,
				() -> fileValidator.validate(List.of(mock, mock, mock, mock)));
		assertEquals(ErrorMessage.MAX_FILE_COUNT_EXCEEDED, exception.getMessage());
	}

	@Test
	void validate_NullFileName() {
		MultipartFile mock = generateMock();
		when(mock.getOriginalFilename()).thenReturn(null);

		List<FileValidationResult> result = fileValidator.validate(List.of(mock));
		assertEquals(1, result.size());
		assertEquals(1, result.get(0).getErrorMessages().size());
		assertEquals(ErrorMessage.FILE_BLANK_NAME_ERROR, result.get(0).getErrorMessages().get(0));
	}

	@Test
	void validate_InvalidContentType() {
		MultipartFile mock = generateMock();
		when(mock.getContentType()).thenReturn(MediaType.TEXT_PLAIN_VALUE);

		List<FileValidationResult> result = fileValidator.validate(List.of(mock));
		assertEquals(1, result.size());
		assertEquals(1, result.get(0).getErrorMessages().size());
		assertEquals(ErrorMessage.INVALID_CONTENT_TYPE, result.get(0).getErrorMessages().get(0));
	}

	@Test
	void validate_MaxFileSizeExceeded() {
		MultipartFile mock = generateMock();
		when(mock.getSize()).thenReturn(999_999_999L);

		List<FileValidationResult> result = fileValidator.validate(List.of(mock));
		assertEquals(1, result.size());
		assertEquals(1, result.get(0).getErrorMessages().size());
		assertEquals(ErrorMessage.MAX_FILE_SIZE_EXCEEDED, result.get(0).getErrorMessages().get(0));
	}

	@Test
	void validate_MultipleErrors() {
		MultipartFile mock = generateMock();
		when(mock.getOriginalFilename()).thenReturn(null);
		when(mock.getContentType()).thenReturn(MediaType.TEXT_PLAIN_VALUE);
		when(mock.getSize()).thenReturn(999_999_999L);

		List<FileValidationResult> result = fileValidator.validate(List.of(mock));
		assertEquals(1, result.size());
		assertEquals(3, result.get(0).getErrorMessages().size());
		assertEquals(ErrorMessage.FILE_BLANK_NAME_ERROR, result.get(0).getErrorMessages().get(0));
		assertEquals(ErrorMessage.INVALID_CONTENT_TYPE, result.get(0).getErrorMessages().get(1));
		assertEquals(ErrorMessage.MAX_FILE_SIZE_EXCEEDED, result.get(0).getErrorMessages().get(2));
	}

	private MultipartFile generateMock() {
		MultipartFile mock = mock(MultipartFile.class);
		when(mock.getOriginalFilename()).thenReturn("name");
		when(mock.getContentType()).thenReturn(MediaType.APPLICATION_PDF_VALUE);
		when(mock.getSize()).thenReturn(12345L);
		return mock;
	}
}