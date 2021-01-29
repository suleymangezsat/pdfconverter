package com.textkernel.pdfconverter.uploader.storage.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.textkernel.pdfconverter.uploader.storage.entity.FileEntity;

public interface FileRepository extends MongoRepository<FileEntity, String> {

}
