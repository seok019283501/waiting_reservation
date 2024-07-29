package com.skshieldus.waiting_reservation_be.domain.admin.controller;

import com.skshieldus.waiting_reservation_be.common.api.Api;
import com.skshieldus.waiting_reservation_be.db.store.enums.StoreStatus;
import com.skshieldus.waiting_reservation_be.domain.admin.service.AdminService;
import com.skshieldus.waiting_reservation_be.domain.store.dto.StoreInfoResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminApiController {
    private final AdminService adminService;

    //식당 등록
    @PutMapping("/store/{storeId}")
    public Api<String> storeStatusChange(
            @PathVariable int storeId,
            @RequestParam StoreStatus status
    ){
        adminService.storeStatusChange(storeId,status);
        return Api.OK("success");
    }
    @GetMapping("/list/{type}")
    public Api<List<StoreInfoResponse>> storeList(@PathVariable String type){
        List<StoreInfoResponse> responses = adminService.storeList(type);
        return Api.OK(responses);
    }
}
