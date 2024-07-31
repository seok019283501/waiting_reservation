package com.skshieldus.waiting_reservation_be.common.utils;

import com.skshieldus.waiting_reservation_be.common.error.ErrorCode;
import com.skshieldus.waiting_reservation_be.common.exception.ApiException;
import com.skshieldus.waiting_reservation_be.db.storefile.StoreFileEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class FileUtils {
    @Value("${spring.servlet.multipart.location}")
    private String uploadDir;
    public StoreFileEntity parseFileInfo(MultipartFile file,String username,int storeId) throws IOException {

        if (file.isEmpty()){
            throw new ApiException(ErrorCode.BAD_REQUEST,"파일이 없습니다.");
        }



        // 파일을 저장할 디렉터리를 지정
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        ZonedDateTime now = ZonedDateTime.now();
        String storedDir = uploadDir + now.format(dtf);
        File dir = new File(storedDir);
        if (!dir.exists()) {
            dir.mkdir();
        }

        String contentType = file.getContentType();
        if (ObjectUtils.isEmpty(contentType)) {
            throw new ApiException(ErrorCode.BAD_REQUEST,"contentType이 없습니다.");
        }

        String fileExtension = "";
        if (contentType.contains("jpeg")) {
            fileExtension = ".jpg";
        } else if (contentType.contains("png")) {
            fileExtension = ".png";
        } else if (contentType.contains("gif")) {
            fileExtension = ".gif";
        } else {
            throw new ApiException(ErrorCode.BAD_REQUEST,"이미지 파일이 아닙니다.");
        }

        String fileName = System.nanoTime() + fileExtension;

        StoreFileEntity entity = StoreFileEntity.builder()
                .fileSize(Long.toString(file.getSize()))
                .originalFileName(file.getOriginalFilename())
                .filePath(storedDir + "/" + fileName)
                .username(username)
                .storeId(storeId)
                .createdAt(LocalDateTime.now())
                .build();

        // 파일 저장
        dir = new File(storedDir + "/" + fileName);
        file.transferTo(dir);


        return entity;
    }

}
