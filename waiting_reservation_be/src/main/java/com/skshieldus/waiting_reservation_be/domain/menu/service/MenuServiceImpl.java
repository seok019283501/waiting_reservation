package com.skshieldus.waiting_reservation_be.domain.menu.service;

import com.skshieldus.waiting_reservation_be.db.menu.MenuEntity;
import com.skshieldus.waiting_reservation_be.db.menu.MenuRepository;
import com.skshieldus.waiting_reservation_be.domain.menu.dto.MenuInsertRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;

    @Override
    public void insertMenu(MenuInsertRequest request,int storeId) {
        MenuEntity entity = new ModelMapper().map(request,MenuEntity.class);
        entity.setStoreId(storeId);

        menuRepository.save(entity);
    }
}
