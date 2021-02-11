package com.textkernel.pdfconverter.uploader.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.textkernel.pdfconverter.uploader.api.dto.FileError;
import com.textkernel.pdfconverter.uploader.api.dto.FileTaskDto;
import com.textkernel.pdfconverter.uploader.api.dto.FileValidationResult;
import com.textkernel.pdfconverter.uploader.api.dto.response.GetFileTaskDetailResponse;
import com.textkernel.pdfconverter.uploader.api.dto.response.GetFileTasksResponse;
import com.textkernel.pdfconverter.uploader.api.dto.response.UploadFilesResponse;
import com.textkernel.pdfconverter.uploader.api.mapper.FileErrorMapper;
import com.textkernel.pdfconverter.uploader.api.mapper.FileTaskMapper;
import com.textkernel.pdfconverter.uploader.api.service.FileUploadService;
import com.textkernel.pdfconverter.uploader.api.validation.FileValidator;
import com.textkernel.pdfconverter.uploader.core.dto.FileTask;

/**
 * Endpoints for file operations
 */
@RestController
@RequestMapping(value = "/file")
public class FileController {
	private final FileUploadService fileUploadService;
	private final FileValidator fileValidator;

	public FileController(FileUploadService fileUploadService, FileValidator fileValidator) {
		this.fileUploadService = fileUploadService;
		this.fileValidator = fileValidator;
	}

	/**
	 * @return information about all created tasks
	 */
	@GetMapping("/all")
	public GetFileTasksResponse getAllFileTasks() {
		List<FileTaskDto> fileTasks = fileUploadService.listAll().stream()
				.map(FileTaskMapper::mapToFileTaskOverview)
				.collect(Collectors.toList());
		return new GetFileTasksResponse(fileTasks);
	}

	/**
	 * @param ids
	 * 		id of the tasks for which data will be fetched
	 * @return information about created tasks for requested ids
	 */
	@GetMapping
	public GetFileTasksResponse getFileTasks(@RequestParam(value = "ids") List<String> ids) {
		List<FileTaskDto> fileTasks = fileUploadService.list(ids).stream()
				.map(FileTaskMapper::mapToFileTaskOverview)
				.collect(Collectors.toList());
		return new GetFileTasksResponse(fileTasks);
	}

	@GetMapping("/{id}")
	public GetFileTaskDetailResponse getFileTaskDetail(@PathVariable String id) {
		FileTask fileTask = fileUploadService.get(id);
		return new GetFileTaskDetailResponse(FileTaskMapper.mapToFileTaskDetail(fileTask));
	}

	/**
	 * Validates input files via {@link FileValidator#validate}. The endpoint continues processing even if there is one or more files fails validation.
	 * It adds them to {@link UploadFilesResponse#errors} and continues processing with the ones passes validation.
	 *
	 * The endpoint response contains information about which files are being converted and which ones received validation error.
	 *
	 * @param files
	 * 		input files to be converted to plain text
	 * @return response that contains data about which input files are successfully uploaded to be processed and which ones received error
	 */
	@PostMapping
	public UploadFilesResponse uploadFiles(@RequestParam("file") List<MultipartFile> files) {
		List<FileValidationResult> validationResults = fileValidator.validate(files);
		List<MultipartFile> successFiles = validationResults.stream().filter(result -> CollectionUtils.isEmpty(result.getErrorMessages())).map(FileValidationResult::getFile).collect(Collectors.toList());
		List<FileError> fileErrors = validationResults.stream().filter(result -> !CollectionUtils.isEmpty(result.getErrorMessages())).map(FileErrorMapper::mapToFileError).collect(Collectors.toList());
		List<FileTask> fileTasks = new ArrayList<>();
		if (!CollectionUtils.isEmpty(successFiles)) {
			fileTasks = fileUploadService.upload(successFiles);
		}
		UploadFilesResponse uploadFilesResponse = new UploadFilesResponse();
		List<FileTaskDto> fileTaskDtos = fileTasks.stream().map(FileTaskMapper::mapToFileTaskOverview).collect(Collectors.toList());
		uploadFilesResponse.setData(fileTaskDtos);
		uploadFilesResponse.setErrors(fileErrors);
		return uploadFilesResponse;
	}
}
