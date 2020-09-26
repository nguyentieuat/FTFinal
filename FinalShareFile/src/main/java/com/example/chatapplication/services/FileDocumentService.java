package com.example.chatapplication.services;

import com.example.chatapplication.domain.FileDocument;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileDocumentService {
    /**
     * Get all file document enable view
     * @return
     * @param nameSearch
     * @param roleSearch
     */
    List<FileDocument> getAllFileViewAble(String nameSearch, String roleSearch);

    /**
     * Save file upload
     * @param myFile
     */
    void saveFile(MultipartFile myFile);

    /**
     * Get file by id
     * @param fileId
     * @return
     */
    FileDocument findByFileId(Long fileId);

    /**
     * Check rule download
     * @param currentUsername
     * @param fileDocument
     * @return
     */
    boolean checkRuleAccessDownload(String currentUsername, FileDocument fileDocument);

    /**
     * Delete by file Id
     * @param currUsername
     * @param fileDocument
     */
    void deleteFile(String currUsername, FileDocument fileDocument);

    /**
     * Check rule del file
     * @param currentUsername
     * @param fileDocument
     * @return
     */
    boolean checkRuleAccessDel(String currentUsername, FileDocument fileDocument);
}
