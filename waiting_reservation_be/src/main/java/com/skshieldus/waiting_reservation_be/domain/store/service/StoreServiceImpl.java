package com.skshieldus.waiting_reservation_be.domain.store.service;

import com.skshieldus.waiting_reservation_be.common.error.ErrorCode;
import com.skshieldus.waiting_reservation_be.common.exception.ApiException;
import com.skshieldus.waiting_reservation_be.common.utils.JwtUtils;
import com.skshieldus.waiting_reservation_be.db.store.StoreEntity;
import com.skshieldus.waiting_reservation_be.db.store.StoreRepository;
import com.skshieldus.waiting_reservation_be.db.store.enums.StoreStatus;
import com.skshieldus.waiting_reservation_be.db.user.UserEntity;
import com.skshieldus.waiting_reservation_be.db.user.UserRepository;
import com.skshieldus.waiting_reservation_be.db.user.enums.Role;
import com.skshieldus.waiting_reservation_be.domain.store.dto.StoreDetailInfoResponse;
import com.skshieldus.waiting_reservation_be.domain.store.dto.StoreInfoResponse;
import com.skshieldus.waiting_reservation_be.domain.store.dto.StoreRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreServiceImpl implements StoreService{
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    @Override
    public void storeRegister(StoreRegisterRequest request, String authorization) {
        StoreEntity entity = new ModelMapper().map(request, StoreEntity.class);
        String token = authorization.substring(7);
        String username = jwtUtils.getSubjectFromToken(token);
        UserEntity userEntity = Optional.ofNullable(userRepository.findByUsernameAndRole(username, Role.ROLE_OWNER))
                .orElseThrow(()->new ApiException(ErrorCode.BAD_REQUEST));
        entity.setUsername(username);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setStatus(StoreStatus.STATUS_OFF);
        storeRepository.save(entity);
    }

    @Override
    public StoreInfoResponse info(int storeId) {
        StoreEntity entity = Optional.ofNullable(storeRepository.findByIdAndStatus(storeId,StoreStatus.STATUS_ON))
                .orElseThrow(()-> new ApiException(ErrorCode.BAD_REQUEST));
        StoreInfoResponse response = new ModelMapper().map(entity, StoreInfoResponse.class);
        return response;
    }


    @Override
    public List<StoreInfoResponse> storeSearch(String storeName, String address) {
        List<StoreEntity> storeEntityList = null;
        log.debug("",storeName,address);
        if(storeName.equals("none") && address.equals("all")){
            //전체 리스트 검색
            storeEntityList = storeRepository.findAllByStatus(StoreStatus.STATUS_ON);
        } else if (address.equals("all")) {
            //전체 지역 식당 이름 검색
            storeEntityList = storeRepository.searchAllStoreName(storeName,StoreStatus.STATUS_ON);
        }  else if(storeName.equals("none")) {
            //주소 검색
            storeEntityList = storeRepository.searchAddress(address,StoreStatus.STATUS_ON);
        }else{
            //지역 및 식당 이름 검색
            storeEntityList = storeRepository.searchStoreNameAndAddress(storeName,address,StoreStatus.STATUS_ON);
        }
        List<StoreInfoResponse> responses = null;
        if(storeEntityList != null){
            responses = storeEntityList.stream().map(this::toResponse).collect(Collectors.toList());
        }

        return responses;
    }

    public StoreInfoResponse toResponse(StoreEntity storeEntity){
        return Optional.ofNullable(storeEntity)
                .map((it)->{
                    return StoreInfoResponse.builder()
                            .id(storeEntity.getId())
                            .status(storeEntity.getStatus())
                            .storeName(storeEntity.getStoreName())
                            .address(storeEntity.getAddress())
                            .createdAt(storeEntity.getCreatedAt())
                            .openAt(storeEntity.getOpenAt())
                            .build();
                }).orElseThrow(()->new ApiException(ErrorCode.BAD_REQUEST));
    }





}
