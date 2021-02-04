package com.textkernel.pdfconverter.uploader.storage.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;

import com.textkernel.pdfconverter.uploader.core.constant.Status;
import com.textkernel.pdfconverter.uploader.core.dto.FileTask;
import com.textkernel.pdfconverter.uploader.core.service.FileStorageService;
import com.textkernel.pdfconverter.uploader.storage.configuration.ObjectMapperConfiguration;
import com.textkernel.pdfconverter.uploader.storage.entity.FileTaskEntity;
import com.textkernel.pdfconverter.uploader.storage.exception.FileTaskNotFoundException;
import com.textkernel.pdfconverter.uploader.storage.repository.FileRepository;

@Import({ObjectMapperConfiguration.class, FileTaskEntity.class})
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
class FileStorageServiceImplTest {

	@Autowired
	private FileRepository fileRepository;

	private FileTaskEntity stored;
	private FileStorageService fileStorageService;

	@BeforeEach
	void setup() {
		fileStorageService = new FileStorageServiceImpl(fileRepository);

		FileTaskEntity entity = new FileTaskEntity();
		entity.setStatus(Status.INIT);
		entity.setCreatedAt(Instant.now());
		stored = fileRepository.save(entity);
	}

	@AfterEach
	void cleanup() {
		fileRepository.deleteAll();
	}

	@Test
	void create_Success() {
		assertEquals(1, fileRepository.count());

		FileTask fileTask = fileStorageService.create(null, Status.SUCCESS);
		assertNotNull(fileTask.getId());
		assertNull(fileTask.getMessage());
		assertEquals(Status.SUCCESS, fileTask.getStatus());
		assertEquals(2, fileRepository.count());
	}

	@Test
	void update_Success() {
		fileStorageService.update(stored.getId(), Status.SUCCESS, null, "some message");
		Optional<FileTaskEntity> updated = fileRepository.findById(stored.getId());
		assertTrue(updated.isPresent());
		FileTaskEntity entity = updated.get();
		assertEquals(Status.SUCCESS, entity.getStatus());
		assertEquals("some message", entity.getMessage());
	}

	@Test
	void update_InvalidId() {
		FileTaskNotFoundException exception = assertThrows(FileTaskNotFoundException.class,
				() -> fileStorageService.update("invalid", Status.SUCCESS, null, null));
		assertEquals("File task not found for id: invalid", exception.getMessage());
	}

	@Test
	void getAll_Success() {
		List<FileTask> all = fileStorageService.getAll();
		assertEquals(1, all.size());
		assertEquals(stored.getId(), all.get(0).getId());
	}
}