package com.textkernel.pdfconverter.uploader.core.properties;

public interface RabbitmqProperties {
	String getDirectExchangeName();

	String getConvertingQueueName();
	String getConvertingRouteName();
	int getConvertingTtl();

	String getStatusUpdatingQueueName();
	String getStatusUpdatingRouteName();
}
