package com.textkernel.pdfconverter.converter.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.textkernel.pdfconverter.converter.core.properties.RabbitmqProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties("rabbitmq")
public class RabbitmqPropertiesImpl implements RabbitmqProperties {
	private Exchange exchange = new Exchange();
	private Task<String> queue = new Task<>();
	private Task<String> routing = new Task<>();
	private Task<Integer> ttl = new Task<>();
	private Task<Integer> maxRetry = new Task<>();

	@Override
	public String getDirectExchangeName() {
		return exchange.getDirect();
	}

	@Override
	public String getConvertingQueueName() {
		return queue.getConverting();
	}

	@Override
	public String getConvertingRouteName() {
		return routing.getConverting();
	}

	@Override
	public String getStatusUpdatingQueueName() {
		return queue.getStatusUpdating();
	}

	@Override
	public String getStatusUpdatingRouteName() {
		return routing.getStatusUpdating();
	}

	@Override
	public int getConvertingTtl() {
		return ttl.getConverting();
	}

	@Override
	public int getConvertingMaxRetryCount() {
		return maxRetry.getConverting();
	}

	@Getter
	@Setter
	public static class Exchange {
		String direct;
	}

	@Getter
	@Setter
	public static class Task<T> {
		T converting;
		T statusUpdating;
	}
}
