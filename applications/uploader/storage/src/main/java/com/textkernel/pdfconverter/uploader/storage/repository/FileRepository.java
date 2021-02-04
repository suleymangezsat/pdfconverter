package com.textkernel.pdfconverter.uploader.storage.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.textkernel.pdfconverter.uploader.storage.entity.FileTaskEntity;

@Repository
public interface FileRepository extends MongoRepository<FileTaskEntity, String> {

}
