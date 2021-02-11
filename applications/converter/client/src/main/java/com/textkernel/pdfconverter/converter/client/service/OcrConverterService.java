package com.textkernel.pdfconverter.converter.client.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.textkernel.pdfconverter.converter.client.mapper.OcrMapper;
import com.textkernel.pdfconverter.converter.client.model.OcrParsingResponse;
import com.textkernel.pdfconverter.converter.client.properties.OcrProperties;
import com.textkernel.pdfconverter.converter.client.util.FileUtil;
import com.textkernel.pdfconverter.converter.core.dto.Convertable;
import com.textkernel.pdfconverter.converter.core.dto.ConvertingResult;
import com.textkernel.pdfconverter.converter.core.service.ConverterService;

/**
 * Service that is responsible from sending requests OCR API, handle error and convert to internal data model
 */
@Service
public class OcrConverterService implements ConverterService {
	private final RestTemplate restTemplate;
	private final OcrProperties ocrProperties;
	private static final String API_KEY_FIELD = "apiKey";
	private static final String BASE64_IMAGE_FIELD = "base64Image";

	public OcrConverterService(RestTemplate restTemplate, OcrProperties ocrProperties) {
		this.restTemplate = restTemplate;
		this.ocrProperties = ocrProperties;
	}

	/**
	 * Converts requested PDF file to plain text
	 *
	 * @param file
	 * 		input file to be sent to OCR API
	 * @return OCR service response that contains plain text
	 */
	@Override
	public ConvertingResult convert(Convertable file) {
		OcrParsingResponse response = fetchOcrApi(file);
		return OcrMapper.mapToConvertingResult(response);
	}

	private OcrParsingResponse fetchOcrApi(Convertable file) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.set(API_KEY_FIELD, ocrProperties.getApikey());

		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add(BASE64_IMAGE_FIELD, FileUtil.generateBase64Content(file.getResource(), file.getContentType()));

		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
		ResponseEntity<OcrParsingResponse> response = restTemplate.postForEntity(ocrProperties.getUrl(), requestEntity, OcrParsingResponse.class);
		return response.getBody();
	}
}
