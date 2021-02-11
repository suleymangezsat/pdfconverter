package com.textkernel.pdfconverter.converter.client.mapper;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.textkernel.pdfconverter.converter.client.dto.ConvertingResultImpl;
import com.textkernel.pdfconverter.converter.client.model.OcrParsingResponse;
import com.textkernel.pdfconverter.converter.client.model.ParsedResult;
import com.textkernel.pdfconverter.converter.core.constant.ConvertingStatus;
import com.textkernel.pdfconverter.converter.core.dto.ConvertingResult;

/**
 * Mapper utility class to convert response received from OCR API into our own representation
 */
public class OcrMapper {
	private static Map<String, ConvertingStatus> convertingStatusMap = new HashMap<>();

	static {
		convertingStatusMap.put("1", ConvertingStatus.CONVERTED_SUCCESS);
		convertingStatusMap.put("2", ConvertingStatus.CONVERTED_PARTIALLY);
		convertingStatusMap.put("3", ConvertingStatus.CONVERTING_FAIL);
		convertingStatusMap.put("4", ConvertingStatus.CLIENT_FAIL);
	}

	/**
	 * Converts response received from the OCR Service to internal data model
	 *
	 * @param response
	 * 		to be converted
	 * @return {@link ConvertingResult} instance that contains API response
	 */
	public static ConvertingResult mapToConvertingResult(OcrParsingResponse response) {
		ConvertingResultImpl convertingResult = new ConvertingResultImpl();
		convertingResult.setTextPages(extractTextPages(response));
		convertingResult.setStatus(Optional.ofNullable(convertingStatusMap.get(response.getOCRExitCode())).orElse(ConvertingStatus.UNKNOWN));
		convertingResult.setErrorMessages(response.getErrorMessage());
		return convertingResult;
	}

	private static List<String> extractTextPages(OcrParsingResponse response) {
		return Optional.ofNullable(response.getParsedResults()).stream()
				.flatMap(Collection::stream)
				.map(ParsedResult::getParsedText)
				.collect(Collectors.toList());
	}
}
