package com.textkernel.pdfconverter.converter.client.service;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.textkernel.pdfconverter.converter.client.configuration.ClientConfiguration;
import com.textkernel.pdfconverter.converter.client.properties.OcrProperties;
import com.textkernel.pdfconverter.converter.core.constant.ConvertingStatus;
import com.textkernel.pdfconverter.converter.core.dto.Convertable;
import com.textkernel.pdfconverter.converter.core.dto.ConvertingResult;

@SpringBootTest(classes = {ClientConfiguration.class})
@AutoConfigureWireMock(port = 0)
class OcrConverterServiceTest {
	private static final byte[] RESOURCE = "123".getBytes();
	private static final String CONTENT_TYPE = MediaType.APPLICATION_PDF_VALUE;

	@Value("${wiremock.server.port}")
	private int wireMockPort;

	@Mock
	private OcrProperties properties;
	@Mock
	private Convertable convertable;
	@Autowired
	private RestTemplate restTemplate;

	private OcrConverterService converterService;
	private AutoCloseable closeable;

	@BeforeEach
	void setup() {
		closeable = MockitoAnnotations.openMocks(this);
		converterService = new OcrConverterService(restTemplate, properties);

		when(properties.getUrl()).thenReturn(String.format("http://localhost:%d", wireMockPort));
		when(properties.getApikey()).thenReturn("apiKey");

		when(convertable.getContentType()).thenReturn(CONTENT_TYPE);
		when(convertable.getResource()).thenReturn(RESOURCE);
	}

	@AfterEach
	void closeService() throws Exception {
		closeable.close();
	}

	@Test
	void convert_Success() {
		stubFor(post(urlPathMatching("/"))
				.willReturn(aResponse()
						.withStatus(HttpStatus.OK.value())
						.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
						.withBodyFile("ocr_response_success.json")));

		ConvertingResult result = converterService.convert(convertable);

		assertNotNull(result);
		assertNull(result.getErrorMessages());
		assertEquals(ConvertingStatus.CONVERTED_SUCCESS, result.getStatus());
		assertEquals(1, result.getTextPages().size());
	}

	@Test
	void convert_Fail() {
		stubFor(post(urlPathMatching("/"))
				.willReturn(aResponse()
						.withStatus(HttpStatus.OK.value())
						.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
						.withBodyFile("ocr_response_fail.json")));

		ConvertingResult result = converterService.convert(convertable);

		assertNotNull(result);
		assertNull(result.getErrorMessages());
		assertEquals(ConvertingStatus.CLIENT_FAIL, result.getStatus());
	}
}