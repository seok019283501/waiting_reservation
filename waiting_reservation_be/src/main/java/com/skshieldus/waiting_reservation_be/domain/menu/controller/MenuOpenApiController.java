package com.skshieldus.waiting_reservation_be.domain.menu.controller;

import com.skshieldus.waiting_reservation_be.common.api.Api;
import com.skshieldus.waiting_reservation_be.domain.menu.dto.MenuInfoRequest;
import com.skshieldus.waiting_reservation_be.domain.menu.dto.MenuInfoResponse;
import com.skshieldus.waiting_reservation_be.domain.menu.service.MenuService;
import com.skshieldus.waiting_reservation_be.domain.menu.service.MenuServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/open-api/menu")
@RequiredArgsConstructor
public class MenuOpenApiController {
    private final MenuService menuService;
    @GetMapping("/{storeId}")
    public Api<List<MenuInfoResponse>> info(
            @PathVariable int storeId
    ){
        List<MenuInfoResponse> responses = menuService.menuInfo(storeId);
        return Api.OK(responses);
    }

}
