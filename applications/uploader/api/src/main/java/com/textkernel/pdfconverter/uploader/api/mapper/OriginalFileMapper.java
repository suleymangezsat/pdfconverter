package com.textkernel.pdfconverter.uploader.api.mapper;

import java.io.IOException;
import java.util.Optional;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.textkernel.pdfconverter.uploader.api.constant.ErrorMessage;
import com.textkernel.pdfconverter.uploader.api.dto.OriginalFileDto;
import com.textkernel.pdfconverter.uploader.api.exception.FileHandlingException;
import com.textkernel.pdfconverter.uploader.core.dto.OriginalFile;


/**
 * Mapper utility class for converting {@link MultipartFile} to internal data model
 */
public class OriginalFileMapper {

	/**
	 * Validates input file and converts to internal {@link OriginalFile} data model
	 *
	 * @param file
	 *        {@link MultipartFile} to be converted
	 * @return converted {@link OriginalFile} instance
	 *
	 * @throws FileHandlingException
	 * 		in file name is blank or there is access errors when reading file content
	 */
	public static OriginalFile mapToOriginalFile(MultipartFile file) {
		String fileName = StringUtils.cleanPath(Optional.ofNullable(file.getOriginalFilename()).orElseThrow(() -> new FileHandlingException(ErrorMessage.FILE_BLANK_NAME_ERROR)));
		OriginalFileDto originalFile;
		try {
			originalFile = new OriginalFileDto(fileName, file.getBytes(), file.getSize(), file.getContentType());
		} catch (IOException e) {
			throw new FileHandlingException(ErrorMessage.FILE_RESOLVING_ERROR);
		}
		return originalFile;
	}
}
