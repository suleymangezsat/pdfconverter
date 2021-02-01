package com.textkernel.pdfconverter.converter.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParsedResult {
	@JsonProperty("TextOverlay")
	TextOverlay textOverlay;

	@JsonProperty("FileParseExitCode")
	int fileParseExitCode;

	@JsonProperty("ParsedText")
	String parsedText;

	@JsonProperty("ErrorMessage")
	String errorMessage;

	@JsonProperty("ErrorDetails")
	String errorDetails;
}
