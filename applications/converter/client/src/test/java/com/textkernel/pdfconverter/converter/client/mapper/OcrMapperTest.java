package com.textkernel.pdfconverter.converter.client.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.textkernel.pdfconverter.converter.client.model.OcrParsingResponse;
import com.textkernel.pdfconverter.converter.client.model.ParsedResult;
import com.textkernel.pdfconverter.converter.core.constant.ConvertingStatus;
import com.textkernel.pdfconverter.converter.core.dto.ConvertingResult;

class OcrMapperTest {
	@Test
	void mapToConvertingResult_Success() {
		ParsedResult parsedResult = new ParsedResult();
		parsedResult.setParsedText("parsed text");

		OcrParsingResponse ocrResponse = new OcrParsingResponse();
		ocrResponse.setParsedResults(List.of(parsedResult));
		ocrResponse.setOCRExitCode("1");
		ocrResponse.setErrorMessage(null);

		ConvertingResult result = OcrMapper.mapToConvertingResult(ocrResponse);
		assertNull(result.getErrorMessages());
		assertEquals(ConvertingStatus.CONVERTED_SUCCESS, result.getStatus());
		assertEquals(1, result.getTextPages().size());
		assertEquals("parsed text", result.getTextPages().get(0));
	}

	@Test
	void mapToConvertingResult_ClientFail() {
		OcrParsingResponse ocrResponse = new OcrParsingResponse();
		ocrResponse.setParsedResults(List.of());
		ocrResponse.setOCRExitCode("4");
		ocrResponse.setErrorMessage(List.of("error message"));

		ConvertingResult result = OcrMapper.mapToConvertingResult(ocrResponse);
		assertEquals(ConvertingStatus.CLIENT_FAIL, result.getStatus());
		assertEquals(0, result.getTextPages().size());
		assertEquals(1, result.getErrorMessages().size());
		assertEquals("error message", result.getErrorMessages().get(0));
	}

	@Test
	void mapToConvertingResult_UnknownError() {
		OcrParsingResponse ocrResponse = new OcrParsingResponse();
		ocrResponse.setErrorMessage(List.of("error message"));

		ConvertingResult result = OcrMapper.mapToConvertingResult(ocrResponse);
		assertEquals(ConvertingStatus.UNKNOWN, result.getStatus());
		assertEquals(0, result.getTextPages().size());
		assertEquals(1, result.getErrorMessages().size());
		assertEquals("error message", result.getErrorMessages().get(0));
	}
}