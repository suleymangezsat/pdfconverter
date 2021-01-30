package com.textkernel.pdfconverter.converter.client.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties("ocr")
public class OcrProperties {
	private String apikey;
}
