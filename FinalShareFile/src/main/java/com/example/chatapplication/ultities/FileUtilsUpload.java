package com.example.chatapplication.ultities;

import com.example.chatapplication.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class FileUtilsUpload {

    @Value("${upload.path}")
    private String rootDir;

    public String saveFileUpload(String username, MultipartFile file) {
        LocalDateTime date = LocalDateTime.now();
        String dateStr = DateTimeFormatter.ofPattern(Constant.FORMAT_DATE_UNDERSCORE).format(date);

        String pathResult = null;
        try {
            String fileName = FilenameUtils.getName(file.getOriginalFilename());
            StringBuilder sbPathResult = new StringBuilder();
            sbPathResult.append(username)
                    .append(File.separator)
                    .append(dateStr);

            String rootPath = new StringBuilder(rootDir)
                    .append(File.separator)
                    .append(sbPathResult)
                    .toString();
            File directory = new File(rootPath);
            if (!directory.exists() && !directory.mkdirs()) {
                directory.mkdirs();
            }

            Path path = Paths.get(new StringBuilder(rootPath).append(File.separator).append(fileName).toString());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            Path locationResult = Paths.get(sbPathResult.toString());
            pathResult = locationResult.toString();
        } catch (Exception ex) {
            throw new BusinessException("Could not store file" + file.getOriginalFilename()
                    + ". Please try again!");
        }
        return pathResult;
    }
}
