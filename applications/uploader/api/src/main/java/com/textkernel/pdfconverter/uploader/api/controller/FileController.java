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
import com.textkernel.pdfconverter.uploader.api.validation.FileValidator;
import com.textkernel.pdfconverter.uploader.core.dto.FileTask;

@RestController
@RequestMapping(value = "/file")
public class FileController {
	private final FileUploadService fileUploadService;
	private final FileValidator fileValidator;

	public FileController(FileUploadService fileUploadService, FileValidator fileValidator) {
		this.fileUploadService = fileUploadService;
		this.fileValidator = fileValidator;
	}

	@GetMapping("/all")
	public List<FileUploadResponse> listAllFileTasks() {
		return fileUploadService.listAll().stream()
				.map(FileUploadMapper::mapToFileUploadResponse)
				.collect(Collectors.toList());
	}

	@GetMapping
	public List<FileUploadResponse> listFileTasks(@RequestParam(value = "ids") String[] ids) {
		return fileUploadService.list(ids).stream()
				.map(FileUploadMapper::mapToFileUploadResponse)
				.collect(Collectors.toList());
	}

	@PostMapping
	public List<FileUploadResponse> uploadFiles(@RequestParam("file") MultipartFile[] files) {
		fileValidator.validate(files);
		List<FileTask> uploadedFileTasks = fileUploadService.upload(files);
		return uploadedFileTasks.stream().map(FileUploadMapper::mapToFileUploadResponse).collect(Collectors.toList());
	}
}
