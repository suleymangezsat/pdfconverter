package com.textkernel.pdfconverter.converter.client.util;

import java.util.Base64;

public class FileUtil {
	public static String generateBase64Content(byte[] bytes, String contentType) {
		return "data:" + contentType + ";base64," +
				Base64.getEncoder().encodeToString(bytes);
	}
}
