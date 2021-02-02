package com.textkernel.pdfconverter.uploader.api.dto;

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

	private byte[] resource;

	private String contentType;
}
