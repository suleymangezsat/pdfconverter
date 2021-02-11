package com.textkernel.pdfconverter.uploader.api.dto.response;

import com.textkernel.pdfconverter.uploader.api.dto.FileTaskDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetFileTaskDetailResponse implements Response<FileTaskDto> {
	private FileTaskDto data;
}
