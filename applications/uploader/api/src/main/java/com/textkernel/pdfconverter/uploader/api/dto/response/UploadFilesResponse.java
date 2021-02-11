package com.textkernel.pdfconverter.uploader.api.dto.response;

import java.util.List;

import com.textkernel.pdfconverter.uploader.api.dto.FileError;
import com.textkernel.pdfconverter.uploader.api.dto.FileTaskDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadFilesResponse implements Response<List<FileTaskDto>> {
	private List<FileTaskDto> data;
	private List<FileError> errors;
}
