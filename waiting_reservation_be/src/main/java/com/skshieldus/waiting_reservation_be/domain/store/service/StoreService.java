package com.skshieldus.waiting_reservation_be.domain.store.service;

import com.skshieldus.waiting_reservation_be.domain.store.dto.StoreInfoResponse;
import com.skshieldus.waiting_reservation_be.domain.store.dto.StoreRegisterRequest;

public interface StoreService {
    void storeRegist(StoreRegisterRequest request);
    StoreInfoResponse info(int storeId);
}
