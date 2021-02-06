package com.textkernel.pdfconverter.uploader.api.validation;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.textkernel.pdfconverter.uploader.api.constant.ErrorMessage;
import com.textkernel.pdfconverter.uploader.api.exception.FileValidationException;

@Component
public class FileValidator {

	private static final String PDF_CONTENT_TYPE = "application/pdf";

	/**
	 * This Validator validates *just* MultipartFile instances
	 */

	public void validate(MultipartFile[] files) {
		if (files != null && files.length > 0) {
			Arrays.stream(files).forEach(this::validate);
			return;
		}
		throw new FileValidationException(ErrorMessage.NO_FILE_ERROR);
	}

	public void validate(MultipartFile file) {
		Optional.ofNullable(file).orElseThrow(() -> new FileValidationException(ErrorMessage.NO_FILE_ERROR));
		Optional.ofNullable(file.getOriginalFilename()).orElseThrow(() -> new FileValidationException(ErrorMessage.FILE_BLANK_NAME_ERROR));
		if (!PDF_CONTENT_TYPE.equals(file.getContentType())) {
			throw new FileValidationException(ErrorMessage.INVALID_CONTENT_TYPE);
		}
	}

}