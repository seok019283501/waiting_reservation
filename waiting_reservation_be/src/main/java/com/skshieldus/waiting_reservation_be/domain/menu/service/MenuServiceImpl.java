package com.skshieldus.waiting_reservation_be.domain.menu.service;

import com.skshieldus.waiting_reservation_be.db.menu.MenuEntity;
import com.skshieldus.waiting_reservation_be.db.menu.MenuRepository;
import com.skshieldus.waiting_reservation_be.domain.menu.dto.MenuInfoRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    //메뉴 추가
    @Override
    public void insertMenu(MenuInfoRequest request, int storeId) {
        MenuEntity entity = new ModelMapper().map(request,MenuEntity.class);
        entity.setStoreId(storeId);

        menuRepository.save(entity);
    }

    //메뉴 수정
    @Override
    public void putMenu(MenuInfoRequest request, int storeId) {
        MenuEntity entity = new ModelMapper().map(request,MenuEntity.class);
        entity.setStoreId(storeId);
        menuRepository.save(entity);
    }
}
