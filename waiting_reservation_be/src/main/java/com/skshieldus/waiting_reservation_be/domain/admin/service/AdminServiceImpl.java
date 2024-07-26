package com.skshieldus.waiting_reservation_be.domain.admin.service;

import com.skshieldus.waiting_reservation_be.common.error.ErrorCode;
import com.skshieldus.waiting_reservation_be.common.exception.ApiException;
import com.skshieldus.waiting_reservation_be.db.store.StoreEntity;
import com.skshieldus.waiting_reservation_be.db.store.StoreRepository;
import com.skshieldus.waiting_reservation_be.db.store.enums.StoreStatus;
import com.skshieldus.waiting_reservation_be.domain.store.dto.StoreInfoResponse;
import com.skshieldus.waiting_reservation_be.domain.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{
    private final StoreRepository storeRepository;
    private final StoreService storeService;

    //식당 등록 허가
    @Override
    public void storeStatusChange(int id, StoreStatus status) {
        StoreEntity storeEntity = Optional.ofNullable(storeRepository.findById(id))
                .orElseThrow(()->new ApiException(ErrorCode.BAD_REQUEST));
        storeEntity.setStatus(StoreStatus.STATUS_ON);
        storeRepository.save(storeEntity);

    }

    @Override
    public List<StoreInfoResponse> storeList(String type) {
        List<StoreEntity> storeEntityList = null;
        //식당 전체 검색
        if(type.equals("all")){
            storeEntityList = storeRepository.findAll();
        }else if(type.equals("on")){
            //승인 식당 검색
            storeEntityList = storeRepository.findAllByStatus(StoreStatus.STATUS_ON);
        }else if(type.equals("off")){
            //미승인 식당 검색
            storeEntityList = storeRepository.findAllByStatus(StoreStatus.STATUS_OFF);
        }
        List<StoreInfoResponse> storeListResponses = storeEntityList.stream()
                .map(storeService::toResponse).collect(Collectors.toList());
        return storeListResponses;
    }
}
