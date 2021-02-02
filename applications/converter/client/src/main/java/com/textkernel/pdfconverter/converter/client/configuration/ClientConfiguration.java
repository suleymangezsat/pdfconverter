package com.textkernel.pdfconverter.converter.client.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.textkernel.pdfconverter.converter.client.errorhandler.RestTemplateResponseErrorHandler;

@Configuration
public class ClientConfiguration {
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
		return restTemplate;
	}
}
