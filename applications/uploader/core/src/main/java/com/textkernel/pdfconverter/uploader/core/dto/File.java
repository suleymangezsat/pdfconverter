package com.textkernel.pdfconverter.uploader.core.dto;

import java.time.Instant;
import java.util.List;

import com.textkernel.pdfconverter.uploader.core.constant.ConvertingStatus;
import com.textkernel.pdfconverter.uploader.core.constant.FileStatus;

public interface File {
	String getId();

	String getName();

	FileStatus getStatus();

	ConvertingStatus getConvertingStatus();

	List<String> getTextPages();

	byte[] getResource();

	String getContentType();

	List<String> getErrorMessages();

	Instant getCreatedAt();

	void setId(String id);

	void setName(String name);

	void setStatus(FileStatus status);

	void setConvertingStatus(ConvertingStatus convertingStatus);

	void setTextPages(List<String> textPages);

	void setResource(byte[] resource);

	void setContentType(String contentType);

	void setErrorMessages(List<String> errorMessages);

	void setCreatedAt(Instant createdAt);



}
