package com.skshieldus.waiting_reservation_be.common.utils;

import com.skshieldus.waiting_reservation_be.common.error.ErrorCode;
import com.skshieldus.waiting_reservation_be.common.exception.ApiException;
import com.skshieldus.waiting_reservation_be.db.storefile.StoreFileEntity;
import com.skshieldus.waiting_reservation_be.domain.store.dto.StoreFileDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class FileUtils {
    @Value("${spring.servlet.multipart.location}")
    private String uploadDir;
    public List<StoreFileEntity> parseFileInfo(MultipartFile file,String username,int storeId) throws IOException {

        if (file.isEmpty()){
            throw new ApiException(ErrorCode.BAD_REQUEST,"파일이 없습니다.");
        }

        // 파일 정보를 저장할 객체를 생성 => 해당 메서드에서 반환하는 값
        List<StoreFileEntity> fileInfoList = new ArrayList<>();

        // 파일을 저장할 디렉터리를 지정 (날짜별로 저장하고 존재하지 않는 경우 생성)
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        ZonedDateTime now = ZonedDateTime.now();
        String storedDir = uploadDir + now.format(dtf);
        File dir = new File(storedDir);
        if (!dir.exists()) {
            dir.mkdir();
        }

        String contentType = file.getContentType();
        if (ObjectUtils.isEmpty(contentType)) {
            throw new ApiException(ErrorCode.BAD_REQUEST,"파일이 없습니다.");
        }

        // Content-Type을 체크해서 이미지 파일인 경우에 한 해서
        // 지정된 확장자로 저장되도록 설정
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

        // 저장에 사용할 파일 이름을 조합 (현재 시간을 파일명으로 사용)
        String fileName = System.nanoTime() + fileExtension;

        // 파일 정보를 리스트에 저장
        StoreFileEntity entity = StoreFileEntity.builder()
                .fileSize(Long.toString(file.getSize()))
                .originalFileName(file.getOriginalFilename())
                .filePath(storedDir + "/" + fileName)
                .username(username)
                .storeId(storeId)
                .createdAt(LocalDateTime.now())
                .build();
        fileInfoList.add(entity);

        // 파일 저장
        dir = new File(storedDir + "/" + fileName);
        file.transferTo(dir);


        return fileInfoList;
    }

}
