package com.skshieldus.waiting_reservation_be.domain.store.service;

import com.skshieldus.waiting_reservation_be.common.error.ErrorCode;
import com.skshieldus.waiting_reservation_be.common.exception.ApiException;
import com.skshieldus.waiting_reservation_be.db.store.StoreEntity;
import com.skshieldus.waiting_reservation_be.db.store.StoreRepository;
import com.skshieldus.waiting_reservation_be.db.store.enums.StoreStatus;
import com.skshieldus.waiting_reservation_be.db.user.UserEntity;
import com.skshieldus.waiting_reservation_be.db.user.UserRepository;
import com.skshieldus.waiting_reservation_be.db.user.enums.Role;
import com.skshieldus.waiting_reservation_be.domain.store.dto.StoreInfoResponse;
import com.skshieldus.waiting_reservation_be.domain.store.dto.StoreRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreServiceImpl implements StoreService{
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    @Override
    public void storeRegist(StoreRegisterRequest request) {
        StoreEntity entity = new ModelMapper().map(request, StoreEntity.class);
        UserEntity userEntity = userRepository.findByUsernameAndRole(request.getUsername(), Role.ROLE_OWNER);
        if(userEntity == null){
            throw new ApiException(ErrorCode.BAD_REQUEST);
        }
        entity.setCreatedAt(LocalDateTime.now());
        entity.setStatus(StoreStatus.STATUS_OFF);
        var ffff = storeRepository.save(entity);
        log.debug("",ffff);
    }

    @Override
    public StoreInfoResponse info(int storeId) {
        StoreEntity entity = storeRepository.findById(storeId);
        if(entity == null){
            throw new ApiException(ErrorCode.NULL_POINT);
        }
        StoreInfoResponse response = new ModelMapper().map(entity, StoreInfoResponse.class);
        return response;
    }
}
