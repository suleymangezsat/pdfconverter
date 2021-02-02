package com.textkernel.pdfconverter.converter.queue.dto;

import com.textkernel.pdfconverter.converter.core.constant.Status;
import com.textkernel.pdfconverter.converter.core.dto.Converted;
import com.textkernel.pdfconverter.converter.core.dto.ConvertingResult;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusUpdatingPayload implements Converted {
	private String id;
	private ConvertingResult convertingResult;
	private Status status;
	private String message;
}
