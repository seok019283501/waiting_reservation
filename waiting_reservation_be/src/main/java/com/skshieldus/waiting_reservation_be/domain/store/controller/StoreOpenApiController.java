package com.skshieldus.waiting_reservation_be.domain.store.controller;

import com.skshieldus.waiting_reservation_be.common.api.Api;
import com.skshieldus.waiting_reservation_be.domain.store.dto.StoreDetailInfoResponse;
import com.skshieldus.waiting_reservation_be.domain.store.dto.StoreInfoResponse;
import com.skshieldus.waiting_reservation_be.domain.store.service.StoreService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/open-api/store")
@RequiredArgsConstructor
public class StoreOpenApiController {
    private final StoreService storeService;

    @GetMapping("/search")
    public Api<List<StoreInfoResponse>> storeSearch(@RequestParam(name = "store_name",defaultValue = "none") String storeName , @RequestParam(defaultValue = "all") String address){
        List<StoreInfoResponse> response = storeService.storeSearch(storeName,address);
        return Api.OK(response);
    }


}
