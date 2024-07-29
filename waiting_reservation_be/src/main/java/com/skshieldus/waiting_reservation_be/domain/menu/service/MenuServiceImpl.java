package com.skshieldus.waiting_reservation_be.domain.menu.service;

import com.skshieldus.waiting_reservation_be.common.error.ErrorCode;
import com.skshieldus.waiting_reservation_be.common.exception.ApiException;
import com.skshieldus.waiting_reservation_be.common.utils.JwtUtils;
import com.skshieldus.waiting_reservation_be.db.menu.MenuEntity;
import com.skshieldus.waiting_reservation_be.db.menu.MenuRepository;
import com.skshieldus.waiting_reservation_be.db.store.StoreEntity;
import com.skshieldus.waiting_reservation_be.db.store.StoreRepository;
import com.skshieldus.waiting_reservation_be.domain.menu.dto.MenuInfoRequest;
import com.skshieldus.waiting_reservation_be.domain.menu.dto.MenuInfoResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;
    private final JwtUtils jwtUtils;
    //메뉴 추가
    @Override
    public void insertMenu(MenuInfoRequest request, int storeId,String authorization) {
        MenuEntity entity = new ModelMapper().map(request,MenuEntity.class);
        entity.setStoreId(storeId);

        menuRepository.save(entity);
    }

    //메뉴 수정
    @Override
    public void putMenu(MenuInfoRequest request,int storeId, int menuId,String authorization) {
        String token = authorization.substring(7);
        String username = jwtUtils.getSubjectFromToken(token);
        //식당 유무 확인 및 owner 확인
        StoreEntity storeEntity = Optional.ofNullable(storeRepository.findByIdAndUsername(storeId,username))
                .orElseThrow(()->new ApiException(ErrorCode.BAD_REQUEST));

        //menu 유무 확인
        Optional.ofNullable(menuRepository.findById(menuId))
                .orElseThrow(()->new ApiException(ErrorCode.BAD_REQUEST));
        MenuEntity entity = new ModelMapper().map(request,MenuEntity.class);
        entity.setStoreId(menuId);
        menuRepository.save(entity);
    }

    //메뉴 삭제
    @Override
    public void deleteMenu(int storeId, int menuId, String authorization) {
        String token = authorization.substring(7);
        String username = jwtUtils.getSubjectFromToken(token);
        //식당 유무 확인 및 owner 확인
        StoreEntity storeEntity = Optional.ofNullable(storeRepository.findByIdAndUsername(storeId,username))
                .orElseThrow(()->new ApiException(ErrorCode.BAD_REQUEST));

        if(!username.equals(storeEntity.getUsername())){
            throw new ApiException(ErrorCode.UNAUTHORIZED,"잘못된 사용자입니다.");
        }
        MenuEntity menuEntity = Optional.ofNullable(menuRepository.findById(menuId))
                .orElseThrow(()->new ApiException(ErrorCode.BAD_REQUEST));

        menuRepository.save(menuEntity);
    }

    @Override
    public List<MenuInfoResponse> menuInfo(int storeId) {
        List<MenuEntity> menuEntity = menuRepository.findAllByStoreId(storeId);
        List<MenuInfoResponse> responses = menuEntity.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return responses;
    }

    public MenuInfoResponse toResponse(MenuEntity menuEntity){
        return Optional.ofNullable(menuRepository)
                .map(it->{
                    return MenuInfoResponse.builder()
                            .id(menuEntity.getId())
                            .storeId(menuEntity.getStoreId())
                            .title(menuEntity.getTitle())
                            .description(menuEntity.getDescription())
                            .cost(menuEntity.getCost())
                            .build();
                }).orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));
    }

}
