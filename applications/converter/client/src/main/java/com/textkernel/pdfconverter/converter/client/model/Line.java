package com.textkernel.pdfconverter.converter.client.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

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
