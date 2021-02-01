package com.textkernel.pdfconverter.converter.client.dto;

import java.util.List;

import com.textkernel.pdfconverter.converter.core.constant.ConvertingStatus;
import com.textkernel.pdfconverter.converter.core.dto.ConvertingResult;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConvertingResultImpl implements ConvertingResult {
	private List<String> textPages;
	private ConvertingStatus status;
	private List<String> errorMessages;
}
