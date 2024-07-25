package com.skshieldus.waiting_reservation_be.domain.menu.controller;

import com.skshieldus.waiting_reservation_be.common.api.Api;
import com.skshieldus.waiting_reservation_be.domain.menu.dto.MenuInsertRequest;
import com.skshieldus.waiting_reservation_be.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/open-api/menu")
@RequiredArgsConstructor
public class MenuOpenApiController {
    private final MenuService menuService;

    @PostMapping("/{storeId}")
    public Api<String> insertMenu(MenuInsertRequest request, @PathVariable int storeId){
        menuService.insertMenu(request, storeId);
        return Api.OK("success");
    }

}
