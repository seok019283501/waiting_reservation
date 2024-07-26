package com.skshieldus.waiting_reservation_be.domain.store.controller;

import com.skshieldus.waiting_reservation_be.common.api.Api;
import com.skshieldus.waiting_reservation_be.domain.store.dto.StoreDetailInfoResponse;
import com.skshieldus.waiting_reservation_be.domain.store.dto.StoreInfoResponse;
import com.skshieldus.waiting_reservation_be.domain.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/open-api/store")
@RequiredArgsConstructor
public class StoreOpenApiController {
    private final StoreService storeService;

    @GetMapping("/list")
    public Api<List<StoreInfoResponse>> storeList(){
        List<StoreInfoResponse> responses = storeService.storeList();
        return Api.OK(responses);
    }

}
