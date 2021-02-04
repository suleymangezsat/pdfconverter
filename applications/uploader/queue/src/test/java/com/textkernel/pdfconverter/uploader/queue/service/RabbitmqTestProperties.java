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
 *******************************************************************************/
package com.textkernel.pdfconverter.uploader.queue.service;

import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.textkernel.pdfconverter.uploader.core.properties.RabbitmqProperties;

@Configuration
public class RabbitmqTestProperties {

	@Bean
	public RabbitmqProperties rabbitProperties() {
		RabbitmqProperties properties = Mockito.mock(RabbitmqProperties.class);
		when(properties.getDirectExchangeName()).thenReturn("exchange");
		when(properties.getConvertingQueueName()).thenReturn("convert");
		when(properties.getConvertingRouteName()).thenReturn("convert-route");
		when(properties.getStatusUpdatingQueueName()).thenReturn("status-update");
		when(properties.getStatusUpdatingRouteName()).thenReturn("status-update-route");
		when(properties.getConvertingTtl()).thenReturn(5000);
		return properties;
	}
}
