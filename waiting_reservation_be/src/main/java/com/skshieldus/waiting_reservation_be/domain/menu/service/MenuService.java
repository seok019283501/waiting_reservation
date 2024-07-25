package com.skshieldus.waiting_reservation_be.domain.menu.service;

import com.skshieldus.waiting_reservation_be.domain.menu.dto.MenuInsertRequest;

public interface MenuService {
    void insertMenu(MenuInsertRequest request, int storeId);
}
