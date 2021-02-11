package com.textkernel.pdfconverter.uploader.core.service;

import java.util.List;

import com.textkernel.pdfconverter.uploader.core.constant.Status;
import com.textkernel.pdfconverter.uploader.core.dto.ConvertingResult;
import com.textkernel.pdfconverter.uploader.core.dto.FileTask;
import com.textkernel.pdfconverter.uploader.core.dto.OriginalFile;

/**
 * Persistence service responsible for retrieving and storing {@link FileTask} entities
 */
public interface FileStorageService {

	/**
	 * Converts original files to entity and stored them with given initial status
	 *
	 * @param originalFiles
	 * 		to be persisted
	 * @param status
	 * 		initial status to insert records with
	 * @return list of inserted entities
	 */
	List<FileTask> create(List<OriginalFile> originalFiles, Status status);

	/**
	 * Updates a record with given id
	 *
	 * @param id
	 * 		PK of the document to be updated
	 * @param status
	 * 		status to set
	 * @param convertingResult
	 * 		convertingResult to set
	 * @param message
	 * 		message to set
	 */
	void update(String id, Status status, ConvertingResult convertingResult, String message);

	/**
	 * Retrieved records with given ids
	 *
	 * @param ids
	 * 		PK list to retrieve records
	 * @return entities matching requested IDs
	 */
	List<FileTask> get(List<String> ids);

	/**
	 * @param id
	 * 		PK of record to be retrieved
	 * @return entity matching requested ID
	 */
	FileTask get(String id);

	/**
	 * @return all entities database
	 */
	List<FileTask> getAll();
}
