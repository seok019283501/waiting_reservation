package com.skshieldus.waiting_reservation_be.domain.store.controller;

import com.skshieldus.waiting_reservation_be.common.api.Api;
import com.skshieldus.waiting_reservation_be.domain.store.dto.StoreDetailInfoResponse;
import com.skshieldus.waiting_reservation_be.domain.store.dto.StoreInfoResponse;
import com.skshieldus.waiting_reservation_be.domain.store.dto.StoreRegisterRequest;
import com.skshieldus.waiting_reservation_be.domain.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StoreApiController {
    private final StoreService storeService;

    @PostMapping("/owner/register")
    public Api<String> register(StoreRegisterRequest request, @RequestHeader("Authorization") String authorization){
        storeService.storeRegister(request,authorization);
        return Api.OK("success");
    }
    @GetMapping("/{storeId}")
    public Api<StoreInfoResponse> info(@PathVariable int storeId){
        StoreInfoResponse response = storeService.info(storeId);
        return Api.OK(response);
    }

}
