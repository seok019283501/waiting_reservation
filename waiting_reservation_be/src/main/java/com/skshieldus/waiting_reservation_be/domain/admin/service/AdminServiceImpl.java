package com.skshieldus.waiting_reservation_be.domain.admin.service;

import com.skshieldus.waiting_reservation_be.common.error.ErrorCode;
import com.skshieldus.waiting_reservation_be.common.exception.ApiException;
import com.skshieldus.waiting_reservation_be.db.store.StoreEntity;
import com.skshieldus.waiting_reservation_be.db.store.StoreRepository;
import com.skshieldus.waiting_reservation_be.db.store.enums.StoreStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{
    private final StoreRepository storeRepository;

    //식당 등록 허가
    @Override
    public void storeStatusChange(int id, StoreStatus status) {
        StoreEntity storeEntity = Optional.ofNullable(storeRepository.findById(id))
                .orElseThrow(()->new ApiException(ErrorCode.BAD_REQUEST));
        storeEntity.setStatus(StoreStatus.STATUS_ON);
        storeRepository.save(storeEntity);

    }
}
