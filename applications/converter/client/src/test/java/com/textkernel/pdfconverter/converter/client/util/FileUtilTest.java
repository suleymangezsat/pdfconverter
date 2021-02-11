package com.textkernel.pdfconverter.converter.client.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class FileUtilTest {

	@Test
	void generateBase64Content() {
		String expected = "data:text/plain;base64,SGVsbG8gV29ybGQ=";
		String actual = FileUtil.generateBase64Content("Hello World".getBytes(), MediaType.TEXT_PLAIN_VALUE);
		assertEquals(expected, actual);
	}
}