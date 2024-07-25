package com.skshieldus.waiting_reservation_be.domain.menu.service;

import com.skshieldus.waiting_reservation_be.domain.menu.dto.MenuInfoRequest;

public interface MenuService {
    void insertMenu(MenuInfoRequest request, int storeId);
    void putMenu(MenuInfoRequest request, int menuId);
    void deleteMenu(int menuId,String authorization);
}
