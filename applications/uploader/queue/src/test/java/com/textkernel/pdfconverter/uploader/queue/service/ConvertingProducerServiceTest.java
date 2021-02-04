package com.textkernel.pdfconverter.uploader.queue.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.test.TestRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.textkernel.pdfconverter.uploader.core.properties.RabbitmqProperties;
import com.textkernel.pdfconverter.uploader.queue.configuration.BindingConfiguration;
import com.textkernel.pdfconverter.uploader.queue.configuration.ExchangeConfiguration;
import com.textkernel.pdfconverter.uploader.queue.configuration.ProducerConfiguration;
import com.textkernel.pdfconverter.uploader.queue.configuration.QueueConfiguration;
import com.textkernel.pdfconverter.uploader.queue.dto.ConvertingPayload;


@SpringBootTest(classes = {
		RabbitmqTestProperties.class,
		RabbitTestConfig.class,
		ConvertingProducerService.class,
		QueueConfiguration.class,
		ExchangeConfiguration.class,
		BindingConfiguration.class,
		ProducerConfiguration.class
})
class ConvertingProducerServiceTest {
	private static final String ID = "123";
	private static final byte[] RESOURCE = new byte[0];
	private static final String CONTENT_TYPE = MediaType.APPLICATION_PDF_VALUE;

	@Autowired
	private TestRabbitTemplate rabbitTemplate;

	@Autowired
	private RabbitTestConfig config;

	@Autowired
	private RabbitmqProperties rabbitProperties;

	private ObjectMapper mapper;

	private ConvertingProducerService producerService;
	private AutoCloseable closeable;

	@BeforeEach
	public void setup() {
		closeable = MockitoAnnotations.openMocks(this);
		mapper = new ObjectMapper();
		producerService = new ConvertingProducerService(rabbitTemplate, rabbitProperties);
	}

	@AfterEach
	void closeService() throws Exception {
		closeable.close();
	}

	@Test
	void sendFileToConvertingQueue_Success() throws JsonProcessingException {
		ConvertingPayload expected = new ConvertingPayload();
		expected.setId(ID);
		expected.setResource(RESOURCE);
		expected.setContentType(CONTENT_TYPE);

		producerService.sendFileToConvertingQueue(ID, RESOURCE, CONTENT_TYPE);

		assertEquals(mapper.writeValueAsString(expected), config.getLastMessageContent());
	}
}