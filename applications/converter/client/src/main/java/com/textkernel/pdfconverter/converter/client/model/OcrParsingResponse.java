package com.textkernel.pdfconverter.converter.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OcrParsingResponse {
	@JsonProperty("ParsedResults")
	List<ParsedResult> parsedResults;

	@JsonProperty("OCRExitCode")
	String oCRExitCode;

	@JsonProperty("IsErroredOnProcessing")
	boolean isErroredOnProcessing;

	@JsonProperty("ErrorMessage")
	List<String> errorMessage;

	@JsonProperty("ErrorDetails")
	List<String> errorDetails;

	@JsonProperty("SearchablePDFURL")
	String searchablePDFURL;

	@JsonProperty("ProcessingTimeInMilliseconds")
	String processingTimeInMilliseconds;
}
