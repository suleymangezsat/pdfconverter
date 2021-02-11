package com.textkernel.pdfconverter.uploader.storage.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;

@TestConfiguration
public class ObjectMapperConfiguration {
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}
