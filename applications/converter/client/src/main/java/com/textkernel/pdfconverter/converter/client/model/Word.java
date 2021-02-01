package com.textkernel.pdfconverter.converter.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Word {
	@JsonProperty("WordText")
	String wordText;

	@JsonProperty("Left")
	int left;

	@JsonProperty("Top")
	int top;

	@JsonProperty("Height")
	int height;

	@JsonProperty("Width")
	int width;
}
