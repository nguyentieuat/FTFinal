package com.example.chatapplication.repositories;

import com.example.chatapplication.domain.FileDocument;
import com.example.chatapplication.domain.RoleAccess;
import com.example.chatapplication.domain.User;
import com.example.chatapplication.domain.UserAccessFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAccessFileRepository extends JpaRepository<UserAccessFile, Long> {
    List<UserAccessFile> findAllByUserAndRoleAccess(User user, RoleAccess roleAccess);

    List<UserAccessFile> findAllByUser(User currUser);

    UserAccessFile findByFileDocumentAndUser(FileDocument filedocument, User user);

    void deleteByFileDocumentAndUser(FileDocument fileDocument, User user);
}
