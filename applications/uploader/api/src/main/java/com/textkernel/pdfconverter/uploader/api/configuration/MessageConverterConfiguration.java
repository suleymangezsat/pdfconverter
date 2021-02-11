package com.textkernel.pdfconverter.uploader.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Configuration class responsible for creating beans that tells how to (de)serialize request/response
 */
@Configuration
public class MessageConverterConfiguration {

	/**
	 * Creates an {@link ObjectMapper} bean for (de)serializing objects from/to json
	 */
	@Bean
	@Primary
	public ObjectMapper objectMapper() {
		JavaTimeModule timeModule = new JavaTimeModule();

		return new ObjectMapper()
				.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
				.registerModule(timeModule)
				.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}

	/**
	 * Creates a {@link MappingJackson2HttpMessageConverter} bean to convert endpoint requests and responses
	 */
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		jsonConverter.setObjectMapper(objectMapper);
		return jsonConverter;
	}
}
