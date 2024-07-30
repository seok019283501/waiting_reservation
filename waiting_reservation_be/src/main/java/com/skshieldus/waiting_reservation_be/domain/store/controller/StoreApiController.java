package com.skshieldus.waiting_reservation_be.domain.store.controller;

import com.skshieldus.waiting_reservation_be.common.api.Api;
import com.skshieldus.waiting_reservation_be.common.error.ErrorCode;
import com.skshieldus.waiting_reservation_be.common.exception.ApiException;
import com.skshieldus.waiting_reservation_be.domain.store.dto.StoreDetailInfoResponse;
import com.skshieldus.waiting_reservation_be.domain.store.dto.StoreFileResponse;
import com.skshieldus.waiting_reservation_be.domain.store.dto.StoreInfoResponse;
import com.skshieldus.waiting_reservation_be.domain.store.dto.StoreRegisterRequest;
import com.skshieldus.waiting_reservation_be.domain.store.service.StoreService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StoreApiController {
    private final StoreService storeService;

    @PostMapping("/owner/register")
    public Api<String> register(@RequestPart(value="data") StoreRegisterRequest storeRegisterRequest,
                                @RequestHeader("Authorization") String authorization,
                                @RequestPart(value="file", required=false) MultipartFile file
    ) throws Exception {
        storeService.storeRegister(storeRegisterRequest,authorization, file);
        return Api.OK("success");
    }
    @GetMapping("/{storeId}")
    public Api<StoreInfoResponse> info(@PathVariable int storeId){
        StoreInfoResponse response = storeService.info(storeId);
        return Api.OK(response);
    }


    //파일 정보
    @GetMapping("/file/{storeId}")
    public Api<StoreFileResponse> fileInfo(@PathVariable int storeId) {
        StoreFileResponse storeFileResponse = storeService.fileInfo(storeId);
        return Api.OK(storeFileResponse);

    }
    //사업자 등록증 다운로드
    @GetMapping("/download/{storeId}")
    public void downloadBusinessRegistration(@PathVariable int storeId, HttpServletResponse response) throws IOException {
        StoreFileResponse storeFileResponse = storeService.downloadBusinessRegistration(storeId);
        if (ObjectUtils.isEmpty(storeFileResponse)) {
            throw new ApiException(ErrorCode.BAD_REQUEST,"파일이 없습니다.");
        }

        Path path = Paths.get(storeFileResponse.getFilePath());
        byte[] file = Files.readAllBytes(path);

        response.setContentType("application/octet-stream");
        response.setContentLength(file.length);
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(storeFileResponse.getOriginalFileName(), "UTF-8") + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");

        response.getOutputStream().write(file);
        response.getOutputStream().flush();
        response.getOutputStream().close();

    }

    //사업자 식당 검색
    @GetMapping("/search")
    public Api<List<StoreInfoResponse>> storeSearch(@RequestParam(name = "store_name",defaultValue = "none") String storeName , @RequestParam(defaultValue = "all") String address, @RequestHeader("Authorization") String authorization){
        List<StoreInfoResponse> response = storeService.storeOwnerSearch(storeName,address,authorization);
        return Api.OK(response);
    }

}
