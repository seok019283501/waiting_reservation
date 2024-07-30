package com.skshieldus.waiting_reservation_be.domain.store.service;

import com.skshieldus.waiting_reservation_be.common.error.ErrorCode;
import com.skshieldus.waiting_reservation_be.common.exception.ApiException;
import com.skshieldus.waiting_reservation_be.common.utils.FileUtils;
import com.skshieldus.waiting_reservation_be.common.utils.JwtUtils;
import com.skshieldus.waiting_reservation_be.db.store.StoreEntity;
import com.skshieldus.waiting_reservation_be.db.store.StoreRepository;
import com.skshieldus.waiting_reservation_be.db.store.enums.StoreStatus;
import com.skshieldus.waiting_reservation_be.db.storefile.StoreFileEntity;
import com.skshieldus.waiting_reservation_be.db.storefile.StoreFileRepository;
import com.skshieldus.waiting_reservation_be.db.user.UserEntity;
import com.skshieldus.waiting_reservation_be.db.user.UserRepository;
import com.skshieldus.waiting_reservation_be.db.user.enums.Role;
import com.skshieldus.waiting_reservation_be.domain.store.dto.StoreDetailInfoResponse;
import com.skshieldus.waiting_reservation_be.domain.store.dto.StoreFileResponse;
import com.skshieldus.waiting_reservation_be.domain.store.dto.StoreInfoResponse;
import com.skshieldus.waiting_reservation_be.domain.store.dto.StoreRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
    private final StoreFileRepository storeFileRepository;
    private final JwtUtils jwtUtils;
    private final FileUtils fileUtils;

    @Override
    public StoreFileResponse fileInfo(int storeId) {
        StoreFileEntity entity = storeFileRepository.findByStoreId(storeId);
        StoreFileResponse response = new ModelMapper().map(entity,StoreFileResponse.class);
        return response;
    }

    //식당 등록
    @Override
    @Transactional
    public void storeRegister(StoreRegisterRequest storeRegisterRequest,String authorization, MultipartFile file) throws Exception {


        StoreEntity entity = new ModelMapper().map(storeRegisterRequest, StoreEntity.class);
        String token = authorization.substring(7);
        String username = jwtUtils.getSubjectFromToken(token);
        //사업자 유무 확인
        Optional.ofNullable(userRepository.findByUsernameAndRole(username, Role.ROLE_OWNER))
                .orElseThrow(()->new ApiException(ErrorCode.BAD_REQUEST));

        entity.setUsername(username);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setStatus(StoreStatus.STATUS_OFF);
        StoreEntity newEntity=storeRepository.save(entity);

        List<StoreFileEntity> list = fileUtils.parseFileInfo(file,username,newEntity.getId());
        storeFileRepository.saveAll(list);
    }

    //파일 경로
    @Override
    public StoreFileResponse downloadBusinessRegistration(int storeId) {
        StoreFileEntity entity = storeFileRepository.findByStoreId(storeId);
        StoreFileResponse response = new ModelMapper().map(entity,StoreFileResponse.class);
        return response;
    }

    //식당 정보
    @Override
    public StoreInfoResponse info(int storeId) {
        StoreEntity entity = Optional.ofNullable(storeRepository.findByIdAndStatus(storeId,StoreStatus.STATUS_ON))
                .orElseThrow(()-> new ApiException(ErrorCode.BAD_REQUEST));
        StoreInfoResponse response = new ModelMapper().map(entity, StoreInfoResponse.class);
        return response;
    }

    //식당 검색
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

    @Override
    public List<StoreInfoResponse> storeOwnerSearch(String storeName, String address, String authorization) {
        List<StoreEntity> storeEntityList = null;
        String token = authorization.substring(7);
        String username = jwtUtils.getSubjectFromToken(token);

        //사업자 유무 확인
        Optional.ofNullable(userRepository.findByUsernameAndRole(username, Role.ROLE_OWNER))
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST));

        log.debug("", storeName, address);
        if (storeName.equals("none") && address.equals("all")) {
            //전체 리스트 검색
            storeEntityList = storeRepository.findAllByUsername(username);
        } else if (address.equals("all")) {
            //전체 지역 식당 이름 검색
            storeEntityList = storeRepository.searchAllOwnerStoreName(storeName, username);
        } else if (storeName.equals("none")) {
            //주소 검색
            storeEntityList = storeRepository.searchOwnerAddress(address, username);
        } else {
            //지역 및 식당 이름 검색
            storeEntityList = storeRepository.searchStoreOwnerNameAndAddress(storeName, address, username);
        }
        List<StoreInfoResponse> responses = null;
        if (storeEntityList != null) {
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
