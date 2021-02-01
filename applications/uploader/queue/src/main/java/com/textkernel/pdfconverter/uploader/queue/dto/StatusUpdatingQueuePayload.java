package com.textkernel.pdfconverter.uploader.queue.dto;

import java.util.List;

import com.textkernel.pdfconverter.uploader.core.constant.ConvertingStatus;
import com.textkernel.pdfconverter.uploader.core.dto.ConvertedFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusUpdatingQueuePayload implements ConvertedFile {
	private String id;
	private List<String> textPages;
	private ConvertingStatus status;
	private List<String> errorMessages;
}
