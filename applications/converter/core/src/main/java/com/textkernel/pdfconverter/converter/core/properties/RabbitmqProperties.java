package com.textkernel.pdfconverter.converter.core.properties;

public interface RabbitmqProperties {
	String getDirectExchangeName();
	String getConvertingQueueName();
	String getConvertingRouteName();
	String getStatusUpdatingQueueName();
	String getStatusUpdatingRouteName();
	int getConvertingTtl();
	int getConvertingMaxRetryCount();
}
