package com.textkernel.pdfconverter.converter.client.configuration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import com.textkernel.pdfconverter.converter.client.errorhandler.RestTemplateResponseErrorHandler;

class ClientConfigurationTest {

	@Test
	void restTemplate() {
		RestTemplate restTemplate = new ClientConfiguration().restTemplate();
		assertNotNull(restTemplate);
		assertTrue(restTemplate.getErrorHandler() instanceof RestTemplateResponseErrorHandler);
	}
}