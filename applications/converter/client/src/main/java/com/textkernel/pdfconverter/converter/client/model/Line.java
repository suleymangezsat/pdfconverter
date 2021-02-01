package com.textkernel.pdfconverter.converter.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Line {
	@JsonProperty("Words")
	List<Word> words;

	@JsonProperty("MaxHeight")
	int maxHeight;

	@JsonProperty("MinTop")
	int minTop;
}
