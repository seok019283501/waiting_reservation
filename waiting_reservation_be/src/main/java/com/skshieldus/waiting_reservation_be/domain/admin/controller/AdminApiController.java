package com.skshieldus.waiting_reservation_be.domain.admin.controller;

import com.skshieldus.waiting_reservation_be.common.api.Api;
import com.skshieldus.waiting_reservation_be.db.store.enums.StoreStatus;
import com.skshieldus.waiting_reservation_be.domain.admin.service.AdminService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminApiController {
    private final AdminService adminService;

    //식당 등록 허가
    @PutMapping("/store/{storeId}")
    public Api<String> storeStatusChange(@PathVariable int storeId, @Parameter StoreStatus status){
        return Api.OK("success");
    }
}
