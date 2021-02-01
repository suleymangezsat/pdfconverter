package com.textkernel.pdfconverter.converter.client.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.textkernel.pdfconverter.converter.client.mapper.OcrMapper;
import com.textkernel.pdfconverter.converter.client.model.OcrParsingResponse;
import com.textkernel.pdfconverter.converter.client.properties.OcrProperties;
import com.textkernel.pdfconverter.converter.client.util.FileUtil;
import com.textkernel.pdfconverter.converter.core.dto.ConvertingResult;
import com.textkernel.pdfconverter.converter.core.dto.OriginalFile;
import com.textkernel.pdfconverter.converter.core.service.ConverterService;

@Service
public class OcrConverterService implements ConverterService {
	private final RestTemplate restTemplate;
	private final OcrProperties ocrProperties;
	private static final String API_URL = "https://api.ocr.space/parse/image";
	private static final String API_KEY_FIELD = "apiKey";
	private static final String BASE64_IMAGE_FIELD = "base64Image";

	public OcrConverterService(RestTemplate restTemplate, OcrProperties ocrProperties) {
		this.restTemplate = restTemplate;
		this.ocrProperties = ocrProperties;
	}

	@Override
	public ConvertingResult convert(OriginalFile file) {
		OcrParsingResponse response = fetchOcrApi(file);
		return OcrMapper.mapToConvertingResult(response);
	}

	private OcrParsingResponse fetchOcrApi(OriginalFile file) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.set(API_KEY_FIELD, ocrProperties.getApikey());

		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add(BASE64_IMAGE_FIELD, FileUtil.generateBase64Content(file.getResource(), file.getContentType()));


		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
		ResponseEntity<OcrParsingResponse> response = restTemplate.postForEntity(API_URL, requestEntity, OcrParsingResponse.class);
		return response.getBody();
	}
}
