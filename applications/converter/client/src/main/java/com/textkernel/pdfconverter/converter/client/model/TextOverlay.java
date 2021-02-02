package com.textkernel.pdfconverter.converter.client.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TextOverlay {
	@JsonProperty("Lines")
	List<Line> lines;

	@JsonProperty("HasOverlay")
	boolean hasOverlay;

	@JsonProperty("Message")
	String message;
}
