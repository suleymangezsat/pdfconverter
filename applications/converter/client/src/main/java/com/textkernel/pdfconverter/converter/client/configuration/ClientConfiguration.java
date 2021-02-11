package com.textkernel.pdfconverter.converter.client.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.textkernel.pdfconverter.converter.client.errorhandler.RestTemplateResponseErrorHandler;

/**
 * Configuration class for Client operations
 */
@Configuration
public class ClientConfiguration {

	/**
	 * Creates a {@link RestTemplate} bean which uses {@link RestTemplateResponseErrorHandler} for error handling
	 *
	 * @return {@link RestTemplate} bean
	 */
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
		return restTemplate;
	}
}
