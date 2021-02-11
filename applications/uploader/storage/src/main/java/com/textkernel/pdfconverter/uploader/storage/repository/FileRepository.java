package com.textkernel.pdfconverter.uploader.storage.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.textkernel.pdfconverter.uploader.storage.entity.FileTaskEntity;

/**
 * Repository class for the storage of file task entities.
 */
@Repository
public interface FileRepository extends MongoRepository<FileTaskEntity, String> {

	@Query("{_id: { $in: ?0 } })")
	List<FileTaskEntity> findByIds(List<String> ids, Sort sort);
}
