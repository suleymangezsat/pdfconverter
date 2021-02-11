package com.textkernel.pdfconverter.converter.queue.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.test.TestRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.textkernel.pdfconverter.converter.core.constant.Status;
import com.textkernel.pdfconverter.converter.core.properties.RabbitmqProperties;
import com.textkernel.pdfconverter.converter.queue.configuration.BindingConfiguration;
import com.textkernel.pdfconverter.converter.queue.configuration.ExchangeConfiguration;
import com.textkernel.pdfconverter.converter.queue.configuration.ProducerConfiguration;
import com.textkernel.pdfconverter.converter.queue.configuration.QueueConfiguration;
import com.textkernel.pdfconverter.converter.queue.dto.StatusUpdatingPayload;

@SpringBootTest(classes = {
		RabbitmqTestProperties.class,
		RabbitTestConfig.class,
		StatusUpdatingProducerService.class,
		QueueConfiguration.class,
		ExchangeConfiguration.class,
		BindingConfiguration.class,
		ProducerConfiguration.class
})
class StatusUpdatingProducerServiceTest {

	@Autowired
	private TestRabbitTemplate rabbitTemplate;

	@Autowired
	private RabbitTestConfig config;

	@Autowired
	private RabbitmqProperties rabbitProperties;

	private ObjectMapper mapper;

	private StatusUpdatingProducerService producerService;
	private AutoCloseable closeable;

	@BeforeEach
	public void setup() {
		closeable = MockitoAnnotations.openMocks(this);
		mapper = new ObjectMapper();
		producerService = new StatusUpdatingProducerService(rabbitProperties, rabbitTemplate);
	}

	@AfterEach
	void closeService() throws Exception {
		closeable.close();
	}

	@Test
	void sendToUpdatingStatusTask_Success() throws JsonProcessingException {
		StatusUpdatingPayload payload = new StatusUpdatingPayload();
		payload.setId("123");
		payload.setMessage("message");
		payload.setStatus(Status.SUCCESS);

		producerService.sendToUpdatingStatusTask(payload);

		assertEquals(mapper.writeValueAsString(payload), config.getLastMessageContent());
	}
}