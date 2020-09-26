package com.example.chatapplication.services.impl;

import com.example.chatapplication.domain.FileDocument;
import com.example.chatapplication.domain.RoleAccess;
import com.example.chatapplication.domain.User;
import com.example.chatapplication.domain.UserAccessFile;
import com.example.chatapplication.repositories.FileDocumentRepository;
import com.example.chatapplication.repositories.RoleAccessRepository;
import com.example.chatapplication.repositories.UserAccessFileRepository;
import com.example.chatapplication.repositories.UserRepository;
import com.example.chatapplication.services.FileDocumentService;
import com.example.chatapplication.ultities.Constant;
import com.example.chatapplication.ultities.FileUtilsUpload;
import com.example.chatapplication.ultities.SecurityUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FileDocumentServiceImpl implements FileDocumentService {
    @Autowired
    private FileDocumentRepository fileDocumentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAccessFileRepository userAccessFileRepository;
    @Autowired
    private RoleAccessRepository roleAccessRepository;
    @Autowired
    private FileUtilsUpload fileUtilsUpload;

    /**
     * Get all file document enable view
     * @return
     * @param nameSearch
     * @param roleSearch
     */
    @Override
    public List<FileDocument> getAllFileViewAble(String nameSearch, String roleSearch) {
        String usernameCurr = SecurityUtils.getAccountCurrentUserLogin().get();
        User currUser = userRepository.findByUsername(usernameCurr);
        List<UserAccessFile> userAccessFiles;
        if (!Objects.isNull(roleSearch)  && !roleSearch.isEmpty()){
            RoleAccess roleAccess = roleAccessRepository.findById(Integer.parseInt(roleSearch)).get();
            userAccessFiles = userAccessFileRepository.findAllByUserAndRoleAccess(currUser, roleAccess);
        } else {
            userAccessFiles = userAccessFileRepository.findAllByUser(currUser);
        }


        if (!userAccessFiles.isEmpty()) {
            List<FileDocument> fileDocuments = userAccessFiles.stream().map(UserAccessFile::getFileDocument).collect(Collectors.toList());
            if (!Objects.isNull(nameSearch) && !nameSearch.isEmpty()){
                return fileDocuments.stream().filter(fileDocument -> fileDocument.getFileName().toLowerCase().contains(nameSearch.toLowerCase())).collect(Collectors.toList());
            }
            return fileDocuments;
        }
        return new ArrayList<>();
    }

    /**
     * Save file upload
     * @param myFile
     */
    @Override
    public void saveFile(MultipartFile myFile) {
        String currUsername = SecurityUtils.getAccountCurrentUserLogin().get();
        User currUser = userRepository.findByUsername(currUsername);
        String path = fileUtilsUpload.saveFileUpload(currUsername, myFile);

        FileDocument fileDocument = new FileDocument();
        fileDocument.setFileName(FilenameUtils.getName(myFile.getOriginalFilename()));
        fileDocument.setFilePath(path);
        fileDocument.setUser(currUser);
        FileDocument fileSave = fileDocumentRepository.save(fileDocument);

        UserAccessFile userAccessFile = new UserAccessFile();
        userAccessFile.setUser(currUser);
        userAccessFile.setFileDocument(fileSave);
        RoleAccess ownner = roleAccessRepository.findById(Constant.ROLE_OWNER).get();
        userAccessFile.setRoleAccess(ownner);
        userAccessFileRepository.save(userAccessFile);
    }

    /**
     * Find file document by id
     * @param fileId
     * @return
     */
    @Override
    public FileDocument findByFileId(Long fileId) {
        return fileDocumentRepository.findById(fileId).get();
    }

    /**
     * Check rule access file
     * @param currentUsername
     * @param fileDocument
     * @return
     */
    @Override
    public boolean checkRuleAccessDownload(String currentUsername, FileDocument fileDocument) {
        User currUser = userRepository.findByUsername(currentUsername);
        UserAccessFile userAccessFile = userAccessFileRepository.findByFileDocumentAndUser(fileDocument, currUser);
        if (!Objects.isNull(userAccessFile)){
            int role = userAccessFile.getRoleAccess().getId();
            if (role == Constant.ROLE_OWNER || role == Constant.ROLE_SHARE){
                return true;
            }
        }
        return false;
    }

    /**
     * Delete filedocument
     * @param currUsername
     * @param fileDocument
     */
    @Override
    @Transactional
    public void deleteFile(String currUsername, FileDocument fileDocument) {
        User currUser = userRepository.findByUsername(currUsername);
        userAccessFileRepository.deleteByFileDocumentAndUser(fileDocument, currUser);
        fileDocumentRepository.delete(fileDocument);
    }

    /**
     * Check rule to del file document
     * @param currentUsername
     * @param fileDocument
     * @return
     */
    @Override
    public boolean checkRuleAccessDel(String currentUsername, FileDocument fileDocument) {
        String currUsername = SecurityUtils.getAccountCurrentUserLogin().get();
        User currUser = userRepository.findByUsername(currUsername);
        UserAccessFile userAccessFile = userAccessFileRepository.findByFileDocumentAndUser(fileDocument, currUser);
        if (!Objects.isNull(userAccessFile)){
            int role = userAccessFile.getRoleAccess().getId();
            if (role == Constant.ROLE_OWNER){
                return true;
            }
        }
        return false;
    }
}
