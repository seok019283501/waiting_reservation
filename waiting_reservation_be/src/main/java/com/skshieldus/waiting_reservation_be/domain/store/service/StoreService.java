package com.skshieldus.waiting_reservation_be.domain.store.service;

import com.skshieldus.waiting_reservation_be.common.api.Api;
import com.skshieldus.waiting_reservation_be.db.store.StoreEntity;
import com.skshieldus.waiting_reservation_be.domain.store.dto.StoreDetailInfoResponse;
import com.skshieldus.waiting_reservation_be.domain.store.dto.StoreInfoResponse;
import com.skshieldus.waiting_reservation_be.domain.store.dto.StoreRegisterRequest;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

public interface StoreService {
    void storeRegister(StoreRegisterRequest request,String authorization);
    StoreInfoResponse info(int storeId);
    StoreInfoResponse toResponse(StoreEntity storeEntity);
    List<StoreInfoResponse> storeSearch(String storeName , String address);
}
