package com.textkernel.pdfconverter.uploader.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.textkernel.pdfconverter.uploader.api.dto.response.FileUploadResponse;
import com.textkernel.pdfconverter.uploader.api.mapper.FileUploadMapper;
import com.textkernel.pdfconverter.uploader.api.service.FileUploadService;

@RestController
@RequestMapping(value = "/file")
public class FileController {
	private final FileUploadService fileUploadService;

	public FileController(FileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	@GetMapping("/list")
	public List<FileUploadResponse> listUploadedFiles() {
		return fileUploadService.listAll().stream().map(FileUploadMapper::mapToFileUploadResponse).collect(Collectors.toList());
	}

	@PostMapping("/upload")
	public FileUploadResponse uploadFile(@RequestParam("file") MultipartFile file) {
		return FileUploadMapper.mapToFileUploadResponse(fileUploadService.upload(file));
	}
}
