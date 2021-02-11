package com.textkernel.pdfconverter.uploader.api.dto.response;

import java.util.List;

import com.textkernel.pdfconverter.uploader.api.dto.FileTaskDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetFileTasksResponse implements Response<List<FileTaskDto>> {
	private List<FileTaskDto> data;
}
