package com.textkernel.pdfconverter.uploader.api.dto;

import java.util.List;

import com.textkernel.pdfconverter.uploader.core.dto.OriginalFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileError {
	OriginalFile originalFile;
	List<String> errorMessages;
}
