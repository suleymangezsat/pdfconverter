package com.textkernel.pdfconverter.uploader.api.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FileValidationResult {
	private MultipartFile file;
	private List<String> errorMessages;
}
