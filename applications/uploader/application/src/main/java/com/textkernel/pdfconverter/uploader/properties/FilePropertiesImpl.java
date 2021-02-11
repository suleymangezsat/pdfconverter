package com.textkernel.pdfconverter.uploader.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.textkernel.pdfconverter.uploader.core.properties.FileProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties("file")
public class FilePropertiesImpl implements FileProperties {
	private Long maxSize;
	private Integer maxCount;

	@Override
	public Long getMaxFileSize() {
		return maxSize;
	}

	@Override
	public Integer getMaxFileCount() {
		return maxCount;
	}
}
