package com.textkernel.pdfconverter.converter.client.mapper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.textkernel.pdfconverter.converter.client.dto.ConvertingResultImpl;
import com.textkernel.pdfconverter.converter.client.model.OcrParsingResponse;
import com.textkernel.pdfconverter.converter.client.model.ParsedResult;
import com.textkernel.pdfconverter.converter.core.constant.ConvertingStatus;
import com.textkernel.pdfconverter.converter.core.dto.ConvertingResult;

public class OcrMapper {
	private static Map<String, ConvertingStatus> convertingStatusMap = new HashMap<>();

	static {
		convertingStatusMap.put("1", ConvertingStatus.CONVERTED_SUCCESS);
		convertingStatusMap.put("2", ConvertingStatus.CONVERTED_PARTIALLY);
		convertingStatusMap.put("3", ConvertingStatus.CONVERTING_FAIL);
		convertingStatusMap.put("4", ConvertingStatus.CLIENT_FAIL);
	}

	public static ConvertingResult mapToConvertingResult(OcrParsingResponse response) {
		ConvertingResultImpl convertingResult = new ConvertingResultImpl();
		Stream<ParsedResult> stream = Optional.ofNullable(response.getParsedResults()).stream().flatMap(Collection::stream);
		convertingResult.setTextPages(stream.map(ParsedResult::getParsedText).collect(Collectors.toList()));
		ConvertingStatus status = Optional.ofNullable(convertingStatusMap.get(response.getOCRExitCode())).orElse(ConvertingStatus.UNKNOWN);
		convertingResult.setStatus(status);
		convertingResult.setErrorMessages(response.getErrorMessage());
		return convertingResult;
	}
}
