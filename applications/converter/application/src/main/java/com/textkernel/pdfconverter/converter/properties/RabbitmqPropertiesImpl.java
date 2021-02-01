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

	@Override
	public String getStatusUpdatingQueueName() {
		return queue.getStatusUpdating();
	}

	@Override
	public String getStatusUpdatingRouteName() {
		return routing.getStatusUpdating();
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
		String statusUpdating;
	}

	@Getter
	@Setter
	public static class Routing {
		String converting;
		String statusUpdating;
	}


}
