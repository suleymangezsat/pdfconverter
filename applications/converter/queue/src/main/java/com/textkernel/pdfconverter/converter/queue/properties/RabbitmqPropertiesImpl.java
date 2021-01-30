package com.textkernel.pdfconverter.converter.queue.properties;


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
	private Queue queue = new Queue();
	private Routing routing = new Routing();

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

	@Getter
	@Setter
	public static class Exchange {
		String direct;
	}

	@Getter
	@Setter
	public static class Queue {
		String converting;
	}

	@Getter
	@Setter
	public static class Routing {
		String converting;
	}


}