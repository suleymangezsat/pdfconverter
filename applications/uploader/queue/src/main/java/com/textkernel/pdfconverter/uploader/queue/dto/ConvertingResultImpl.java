package com.textkernel.pdfconverter.uploader.queue.dto;

import java.util.List;

import com.textkernel.pdfconverter.uploader.core.constant.ConvertingStatus;
import com.textkernel.pdfconverter.uploader.core.dto.ConvertingResult;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConvertingResultImpl implements ConvertingResult {
	private List<String> textPages;
	private ConvertingStatus status;
	private List<String> errorMessages;
}
