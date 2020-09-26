package com.example.chatapplication.controllers;

import com.example.chatapplication.domain.FileDocument;
import com.example.chatapplication.domain.RoleAccess;
import com.example.chatapplication.services.FileDocumentService;
import com.example.chatapplication.services.RoleAccessService;
import com.example.chatapplication.ultities.Constant;
import com.example.chatapplication.ultities.SecurityUtils;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Controller
@Slf4j
public class FileDocumentController {
    @Autowired
    private FileDocumentService fileDocumentService;
    @Autowired
    private RoleAccessService roleAccessService;

    @Value("${upload.path}")
    private String rootDir;

    /**
     * init page list file
     */
    @GetMapping(value = {"/listFile"})
    private String getListFile(HttpServletRequest request){
        List<RoleAccess> roleAccesses = roleAccessService.getAllRole();
        String nameSearch =  request.getParameter(Constant.NameAttribute.NAME_SEARCH);
        String roleSearch =  request.getParameter(Constant.NameAttribute.ROLE_SEARCH);
        List<FileDocument> allFileViewAble = fileDocumentService.getAllFileViewAble( nameSearch, roleSearch);

        request.setAttribute(Constant.NameAttribute.LIST_ROLE_ACCESS, roleAccesses);
        request.setAttribute(Constant.NameAttribute.LIST_FILE_ENABLE, allFileViewAble);
        request.setAttribute(Constant.NameAttribute.NAME_SEARCH, nameSearch);
        request.setAttribute(Constant.NameAttribute.ROLE_SEARCH, roleAccesses);
        return "listFile";
    }

    /**
     * init page list file
     */
    @GetMapping(value = {"/addFile"})
    private String addFile(){
        return "addFile";
    }

    /**
     * init page list file
     */
    @PostMapping(value = {"/addFile"})
    private String addFile(@RequestParam(Constant.NameAttribute.FILE_UPLOAD) MultipartFile myFile, HttpServletRequest request){
        fileDocumentService.saveFile(myFile);
        return "redirect:/listFile";
    }

    /**
     * init page list file
     * @return
     */
    @GetMapping(value = {"/download/{fileId}"})
    private ResponseEntity download(@PathVariable Long fileId, HttpServletResponse response){

        FileDocument fileDocument = fileDocumentService.findByFileId(fileId);
        if (!Objects.isNull(fileDocument)) {
            String currentUsername = SecurityUtils.getAccountCurrentUserLogin().get();
            if (!fileDocumentService.checkRuleAccessDownload(currentUsername, fileDocument)) {
                return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
            }

            String path = new StringBuilder(rootDir)
                    .append(File.separator)
                    .append(fileDocument.getFilePath())
                    .append(File.separator)
                    .append(fileDocument.getFileName())
                    .toString();
            Path fullPath = Paths.get(path);
            File file = fullPath.toFile();
            try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(FileUtils.readFileToByteArray(file));
                 InputStream inputStream = new BufferedInputStream(byteArrayInputStream)) {
                String CONTENT_DISPOSITION_FORMAT = "attachment; filename=\"%s\"; filename*=UTF-8''%s";

                String fileName = fileDocument.getFileName();
                String filenameEncode = MimeUtility.encodeText(fileName, String.valueOf(Charset.forName("UTF-8")), null);

                // Set mimeType return
                response.setCharacterEncoding("UTF-8");
                response.setContentType(String.valueOf(MediaType.APPLICATION_OCTET_STREAM));
                // Set info return
                String header = String.format(CONTENT_DISPOSITION_FORMAT, fileName, filenameEncode);

                response.setHeader("Content-disposition", header);
                FileCopyUtils.copy(inputStream, response.getOutputStream());
                return new ResponseEntity(HttpStatus.OK);
            } catch (IOException e) {
                log.error(ExceptionUtils.getStackTrace(e));
            } finally {
                try {
                    response.getOutputStream().flush();
                    response.flushBuffer();
                } catch (IOException e) {
                    log.error(ExceptionUtils.getStackTrace(e));
                }
            }
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = {"/deleteFile/{fileId}"})
    public String deleteFile(@PathVariable Long fileId){
        String currUsername = SecurityUtils.getAccountCurrentUserLogin().get();
        FileDocument fileDocument = fileDocumentService.findByFileId(fileId);
        boolean hasRoleDel = fileDocumentService.checkRuleAccessDel(currUsername, fileDocument);
        if (hasRoleDel){
            fileDocumentService.deleteFile(currUsername, fileDocument);
        }
        return "redirect:/listFile";
    }
}
