package com.example.myapi.utils;

import com.example.myapi.model.CommonAttach;
import com.example.myapi.model.CommonAttachType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

@Component
@Slf4j
public class FileUploadUtil {

    @Value("my.attach.path")
    public String ROOT_PATH;


    public static CommonAttach upload(MultipartFile file, CommonAttachType type){

        log.info("file name = {}, originalName = {}", file.getName(), file.getOriginalFilename());

        if (file.isEmpty()){
            log.info("file is empty.");
            return null;
        }

        try {
            LocalDate now = LocalDate.now();
            // FIXME getRandom &  확장자
            // 경로: /서비스 type/year/month/filename
            file.transferTo(new File(String.format("/%s/%d/%2d/%s", type.name(), now.getYear(), now.getMonthValue(), file.getOriginalFilename())));

        } catch (IOException e) {
            log.error("IOException {}", file);
        }

        return CommonAttach.builder()
                .fileName(file.getName())
                .filePath(file.getOriginalFilename())
                .type(type)
                .build();
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

