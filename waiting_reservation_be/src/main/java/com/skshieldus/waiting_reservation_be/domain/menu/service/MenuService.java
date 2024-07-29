package com.skshieldus.waiting_reservation_be.domain.menu.service;

import com.skshieldus.waiting_reservation_be.domain.menu.dto.MenuInfoRequest;
import com.skshieldus.waiting_reservation_be.domain.menu.dto.MenuInfoResponse;

import java.util.List;

public interface MenuService {
    void insertMenu(MenuInfoRequest request, int storeId,String authorization);
    void putMenu(MenuInfoRequest request,int storeId, int menuId, String authorization);
    void deleteMenu(int storeId,int menuId,String authorization);
    List<MenuInfoResponse> menuInfo(int storeId);
}
