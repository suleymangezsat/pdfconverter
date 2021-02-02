package com.textkernel.pdfconverter.uploader.storage.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.textkernel.pdfconverter.uploader.storage.entity.FileTaskEntity;

public interface FileRepository extends MongoRepository<FileTaskEntity, String> {

}
