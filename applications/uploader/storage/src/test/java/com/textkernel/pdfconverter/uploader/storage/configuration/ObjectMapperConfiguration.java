/*******************************************************************************
 * Textkernel CONFIDENTIAL
 * __________________
 *
 * Textkernel BV
 * All Rights Reserved.
 *
 * NOTICE: All information contained herein is, and remains
 * the property of Textkernel BV and its suppliers,
 * if any. The intellectual and technical concepts contained
 * herein are proprietary to Textkernel BV
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Textkernel BV.
 *
 *******************************************************************************/

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
