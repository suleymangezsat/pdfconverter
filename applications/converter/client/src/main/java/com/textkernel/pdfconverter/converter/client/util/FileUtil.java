package com.textkernel.pdfconverter.converter.client.util;

import java.util.Base64;

/**
 * Utility class that converts file input to base64 encoded in a way that OCR service expects
 */
public class FileUtil {

	/**
	 * Encodes binary input with Base64 algorithm and generates a String template that contains file's content type and data
	 *
	 * @param bytes
	 * 		binary data to be converted to base64
	 * @param contentType
	 * 		content type of the input file
	 * @return String that contains info about file's content type and data in a way that OCR service expects
	 */
	public static String generateBase64Content(byte[] bytes, String contentType) {
		return String.format("data:%s;base64,%s", contentType,
				Base64.getEncoder().encodeToString(bytes));
	}
}
