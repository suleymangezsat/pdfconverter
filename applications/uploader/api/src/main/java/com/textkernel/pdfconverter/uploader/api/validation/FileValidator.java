package com.textkernel.pdfconverter.uploader.api.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.textkernel.pdfconverter.uploader.api.constant.ErrorMessage;
import com.textkernel.pdfconverter.uploader.api.dto.FileValidationResult;
import com.textkernel.pdfconverter.uploader.api.exception.FileValidationException;
import com.textkernel.pdfconverter.uploader.core.properties.FileProperties;

/**
 * Component that is responsible for validating input files
 */
@Component
public class FileValidator {

	private static final String PDF_CONTENT_TYPE = "application/pdf";
	private final FileProperties fileProperties;

	public FileValidator(FileProperties fileProperties) {
		this.fileProperties = fileProperties;
	}

	/**
	 * Validates {@link MultipartFile} instances. All items in the list MUST HAVE a valid file name
	 * and have pdf content type
	 *
	 * @param files
	 * 		to be validated
	 * @throws FileValidationException
	 * 		if input is empty or one or more of its items fails validation
	 */
	public List<FileValidationResult> validate(List<MultipartFile> files) {
		if (CollectionUtils.isEmpty(files)) {
			throw new FileValidationException(ErrorMessage.NO_FILE_ERROR);
		}
		if (files.size() > fileProperties.getMaxFileCount()) {
			throw new FileValidationException(ErrorMessage.MAX_FILE_COUNT_EXCEEDED);
		}
		return files.stream()
				.map(this::validate)
				.collect(Collectors.toList());
	}

	private FileValidationResult validate(MultipartFile file) {
		List<String> errorMessages = new ArrayList<>();
		if (file.getOriginalFilename() == null) {
			errorMessages.add(ErrorMessage.FILE_BLANK_NAME_ERROR);
		}
		if (!PDF_CONTENT_TYPE.equals(file.getContentType())) {
			errorMessages.add(ErrorMessage.INVALID_CONTENT_TYPE);
		}
		if (file.getSize() > fileProperties.getMaxFileSize()) {
			errorMessages.add(ErrorMessage.MAX_FILE_SIZE_EXCEEDED);
		}
		return new FileValidationResult(file,errorMessages);
	}

}
