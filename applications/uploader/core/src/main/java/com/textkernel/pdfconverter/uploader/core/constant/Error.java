package com.textkernel.pdfconverter.uploader.core.constant;

public enum Error {
	FILE_RESOLVE_ERROR(0, "Unable to resolve file"),
	FILE_NAME_ERROR(1, "File name should not be null");

	private final int code;
	private final String description;

	private Error(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public int getCode() {
		return code;
	}

	@Override
	public String toString() {
		return code + ": " + description;
	}
}
