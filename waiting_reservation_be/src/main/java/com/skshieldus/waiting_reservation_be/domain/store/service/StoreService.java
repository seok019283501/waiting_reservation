package com.skshieldus.waiting_reservation_be.domain.store.service;

import com.skshieldus.waiting_reservation_be.common.api.Api;
import com.skshieldus.waiting_reservation_be.db.store.StoreEntity;
import com.skshieldus.waiting_reservation_be.domain.store.dto.*;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

public interface StoreService {
    StoreFileResponse fileInfo(int storeId);
    void storeRegister(StoreRegisterRequest storeRegisterRequest,String authorization, MultipartFile file) throws Exception;
    StoreFileDto downloadBusinessRegistration(int storeId);
    StoreInfoResponse info(int storeId);
    StoreInfoResponse toResponse(StoreEntity storeEntity);
    List<StoreInfoResponse> storeSearch(String storeName , String address);
    List<StoreInfoResponse> storeOwnerSearch(String storeName , String address, String authorization);
}
