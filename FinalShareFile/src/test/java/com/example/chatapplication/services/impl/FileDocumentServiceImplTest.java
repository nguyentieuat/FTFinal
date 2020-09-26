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
import com.example.chatapplication.ultities.FileUtilsUpload;
import com.example.chatapplication.ultities.SecurityUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@PowerMockIgnore({"javax.management.*", "javax.script.*"})
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class FileDocumentServiceImplTest {

    @Mock
    private FileDocumentRepository fileDocumentRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserAccessFileRepository userAccessFileRepository;
    @Mock
    private RoleAccessRepository roleAccessRepository;
    @Mock
    private FileUtilsUpload fileUtilsUpload;
    @InjectMocks
    private FileDocumentService fileDocumentService = new FileDocumentServiceImpl();

    @Test
    public void getAllFileViewAble() {
        String nameSearch = null;
        String roleSearch = null;

        Optional<String> optionalUsername = Optional.of("user1");
        PowerMockito.mockStatic(SecurityUtils.class);
        when(SecurityUtils.getAccountCurrentUserLogin()).thenReturn(optionalUsername);
        String usernameCurr = SecurityUtils.getAccountCurrentUserLogin().get();
        User currUser = new User();
        currUser.setUsername(usernameCurr);
        when(userRepository.findByUsername(usernameCurr)).thenReturn(currUser);

        RoleAccess roleAccess = new RoleAccess();
        roleAccess.setId(1);
        RoleAccess roleAccess1 = new RoleAccess();
        roleAccess1.setId(2);
        RoleAccess roleAccess2 = new RoleAccess();
        roleAccess2.setId(3);

        List<FileDocument> fileDocuments = new ArrayList<>();
        FileDocument fileDocument = new FileDocument();
        fileDocument.setId(1);
        fileDocument.setFileName("abcdes");
        fileDocuments.add(fileDocument);


        List<UserAccessFile> userAccessFiles =  new ArrayList<>();
        UserAccessFile userAccessFile = new UserAccessFile();
        userAccessFile.setRoleAccess(roleAccess);
        userAccessFile.setFileDocument(fileDocument);
        userAccessFile.setUser(currUser);

        userAccessFiles.add(userAccessFile);
        when(userAccessFileRepository.findAllByUser(currUser)).thenReturn(userAccessFiles);


        Assert.assertEquals(fileDocuments.size(), fileDocumentService.getAllFileViewAble(nameSearch, roleSearch).size());
    }

    @Test
    public void getAllFileViewAble1() {
        String nameSearch = "abc";
        String roleSearch = "1";

        Optional<String> optionalUsername = Optional.of("user1");
        PowerMockito.mockStatic(SecurityUtils.class);
        when(SecurityUtils.getAccountCurrentUserLogin()).thenReturn(optionalUsername);
        String usernameCurr = SecurityUtils.getAccountCurrentUserLogin().get();
        User currUser = new User();
        currUser.setUsername(usernameCurr);
        when(userRepository.findByUsername(usernameCurr)).thenReturn(currUser);

        RoleAccess roleAccess = new RoleAccess();
        roleAccess.setId(1);
        RoleAccess roleAccess1 = new RoleAccess();
        roleAccess1.setId(2);
        RoleAccess roleAccess2 = new RoleAccess();
        roleAccess2.setId(3);

        List<FileDocument> fileDocuments = new ArrayList<>();
        FileDocument fileDocument = new FileDocument();
        fileDocument.setId(1);
        fileDocument.setFileName("abcdes");
        fileDocuments.add(fileDocument);


        List<UserAccessFile> userAccessFiles =  new ArrayList<>();
        UserAccessFile userAccessFile = new UserAccessFile();
        userAccessFile.setRoleAccess(roleAccess);
        userAccessFile.setFileDocument(fileDocument);
        userAccessFile.setUser(currUser);

        userAccessFiles.add(userAccessFile);
        Optional<RoleAccess> optionalRoleAccess = Optional.of(roleAccess);
        when(roleAccessRepository.findById(Integer.parseInt(roleSearch))).thenReturn(optionalRoleAccess);
        roleAccess = optionalRoleAccess.get();

        when(userAccessFileRepository.findAllByUserAndRoleAccess(currUser, roleAccess)).thenReturn(userAccessFiles);

        Assert.assertEquals(fileDocuments.size(), fileDocumentService.getAllFileViewAble(nameSearch, roleSearch).size());
    }

    @Test
    public void saveFile() {
    }

    @Test
    public void findByFileId() {
        FileDocument fileDocument = new FileDocument();
        fileDocument.setId(1);
        fileDocument.setFileName("abcdes");
        Optional<FileDocument> optionalFileDocument = Optional.of(fileDocument);
        when(fileDocumentRepository.findById(1l)).thenReturn(optionalFileDocument);

        Assert.assertEquals(1l, fileDocumentService.findByFileId(1l).getId());
    }

    @Test
    public void checkRuleAccessDownload() {
        String usernameCurr = "user1";
        User user = new User();
        user.setUsername(usernameCurr);
        when(userRepository.findByUsername(usernameCurr)).thenReturn(user);
        FileDocument fileDocument = new FileDocument();
        when(userAccessFileRepository.findByFileDocumentAndUser(fileDocument, user)).thenReturn(null);
        Assert.assertFalse(fileDocumentService.checkRuleAccessDownload(usernameCurr, fileDocument));
    }

    @Test
    public void checkRuleAccessDownload1() {
        String usernameCurr = "user1";
        User user = new User();
        user.setUsername(usernameCurr);
        when(userRepository.findByUsername(usernameCurr)).thenReturn(user);
        FileDocument fileDocument = new FileDocument();
        UserAccessFile userAccessFile = new UserAccessFile();
        RoleAccess roleAccess = new RoleAccess();
        roleAccess.setId(1);
        userAccessFile.setRoleAccess(roleAccess);
        when(userAccessFileRepository.findByFileDocumentAndUser(fileDocument, user)).thenReturn(userAccessFile);
        Assert.assertTrue(fileDocumentService.checkRuleAccessDownload(usernameCurr, fileDocument));
    }

    @Test
    public void deleteFile() {
    }

    @Test
    public void checkRuleAccessDel() {
    }
}