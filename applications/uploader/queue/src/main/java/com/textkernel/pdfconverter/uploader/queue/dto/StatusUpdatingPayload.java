package com.textkernel.pdfconverter.uploader.queue.dto;


import com.textkernel.pdfconverter.uploader.core.constant.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusUpdatingPayload {
	private String id;
	private ConvertingResultImpl convertingResult;
	private Status status;
	private String message;
}
