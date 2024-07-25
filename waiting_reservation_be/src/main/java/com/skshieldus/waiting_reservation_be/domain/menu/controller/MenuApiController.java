package com.skshieldus.waiting_reservation_be.domain.menu.controller;

import com.skshieldus.waiting_reservation_be.common.api.Api;
import com.skshieldus.waiting_reservation_be.domain.menu.dto.MenuInfoRequest;
import com.skshieldus.waiting_reservation_be.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuApiController {
    private final MenuService menuService;

    @PostMapping("/{storeId}")
    public Api<String> insertMenu(MenuInfoRequest request, @PathVariable int storeId){
        menuService.insertMenu(request, storeId);
        return Api.OK("success");
    }
    @PutMapping("/{menuId}")
    public Api<String> putMenu(MenuInfoRequest request, @PathVariable int menuId){
        menuService.putMenu(request,menuId);
        return Api.OK("success");
    }
    @DeleteMapping("/{menuId}")
    public Api<String> deleteMenu(@PathVariable int menuId, @RequestHeader("Authorization") String authorization){
        return Api.OK("success");
    }

}
