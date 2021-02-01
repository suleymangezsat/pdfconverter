package com.textkernel.pdfconverter.converter.client.util;

import java.util.Base64;

public class FileUtil {
	public static String generateBase64Content(byte[] bytes, String contentType) {
		return String.format("data:%s;base64,%s", contentType,
				Base64.getEncoder().encodeToString(bytes));
	}
}
