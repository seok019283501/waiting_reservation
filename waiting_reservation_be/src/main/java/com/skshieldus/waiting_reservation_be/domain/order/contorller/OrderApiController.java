package com.skshieldus.waiting_reservation_be.domain.order.contorller;

import com.skshieldus.waiting_reservation_be.common.api.Api;
import com.skshieldus.waiting_reservation_be.domain.order.dto.OrderResponse;
import com.skshieldus.waiting_reservation_be.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderApiController {
    private final OrderService orderService;

    @PostMapping("/{storeId}/{menuId}")
    public Api<OrderResponse> order(
            @PathVariable int storeId,
            @PathVariable int menuId,
            @RequestHeader("Authorization") String authorization){
        OrderResponse response = orderService.order(storeId,menuId,authorization);
        return Api.OK(response);
    }

}
