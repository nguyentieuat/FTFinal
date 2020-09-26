package com.example.chatapplication.repositories;

import com.example.chatapplication.domain.FileDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDocumentRepository extends JpaRepository<FileDocument, Long> {
}
