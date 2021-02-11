package com.textkernel.pdfconverter.uploader.api.mapper;

import com.textkernel.pdfconverter.uploader.api.dto.FileError;
import com.textkernel.pdfconverter.uploader.api.dto.FileValidationResult;
import com.textkernel.pdfconverter.uploader.core.dto.OriginalFile;

/**
 * Mapper utility class for converting file validation errors
 */
public class FileErrorMapper {

	/**
	 * Converts {@link FileValidationResult} to {@link FileError}
	 *
	 * @param fileValidationResult
	 * 		to be converted
	 * @return converted {@link FileError} instance that contains original input and error messages
	 */
	public static FileError mapToFileError(FileValidationResult fileValidationResult) {
		FileError fileError = new FileError();
		OriginalFile originalFile = OriginalFileMapper.mapToOriginalFile(fileValidationResult.getFile());
		fileError.setOriginalFile(originalFile);
		fileError.setErrorMessages(fileValidationResult.getErrorMessages());
		return fileError;
	}
}
