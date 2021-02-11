package com.textkernel.pdfconverter.uploader.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.textkernel.pdfconverter.uploader.core.dto.OriginalFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OriginalFileDto implements OriginalFile {

	private String name;

	@JsonIgnore
	private byte[] resource;

	private Long size;

	private String contentType;
}
