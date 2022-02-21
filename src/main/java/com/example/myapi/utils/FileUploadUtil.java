package com.example.myapi.utils;

import com.example.myapi.model.CommonAttach;
import com.example.myapi.model.CommonAttachType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

@Component
@Slf4j
public class FileUploadUtil {

    @Value("${my.attach.path}")
    public String ROOT_PATH;

    public CommonAttach upload(MultipartFile file, CommonAttachType type){

        log.info("originalName = {}", file.getOriginalFilename());

        if (file.isEmpty()){
            log.info("file is empty.");
            return null;
        }

        try {
            LocalDate now = LocalDate.now();
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String realName = String.format("%s.%s", getRandom(10), extension);
            String directoryString = String.format("/%s/%d/%02d", type.name(), now.getYear(), now.getMonthValue());;
            String fullPath = ROOT_PATH + directoryString;
            File diretory = new File(fullPath);
            String filePath = String.format("%s/%s", fullPath, realName);
            Path path = Paths.get(filePath);

            if (!diretory.exists()){
                diretory.mkdirs();
            }

            log.info("filePath is {}.", filePath);

            // 경로: /서비스 type/year/month/filename
            file.transferTo(path);

            return CommonAttach.builder()
                    .fileName(file.getOriginalFilename())
                    .filePath(String.format("%s/%s", directoryString, realName))
                    .type(type)
                    .build();
        } catch (IOException e) {
            log.error("IOException {}", file);
            return null;
        }
    }

    private static String getRandom(int length) {

        char[] charaters = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        StringBuffer sb = new StringBuffer();
        Random rn = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(charaters[rn.nextInt(charaters.length)]);
        }
        return sb.toString();
    }
}

