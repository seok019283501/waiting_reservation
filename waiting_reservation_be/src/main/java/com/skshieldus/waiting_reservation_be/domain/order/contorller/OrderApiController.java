package com.skshieldus.waiting_reservation_be.domain.order.contorller;

import com.skshieldus.waiting_reservation_be.common.api.Api;
import com.skshieldus.waiting_reservation_be.db.user.enums.Role;
import com.skshieldus.waiting_reservation_be.domain.order.dto.OrderRequest;
import com.skshieldus.waiting_reservation_be.domain.order.dto.OrderResponse;
import com.skshieldus.waiting_reservation_be.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderApiController {
    private final OrderService orderService;
    //주문
    @PostMapping("/{storeId}")
    public Api<List<OrderResponse>> order(
            @PathVariable int storeId,
            @RequestBody List<OrderRequest> orderRequestList,
            @RequestHeader("Authorization") String authorization
    ){
        List<OrderResponse> response = orderService.order(storeId,orderRequestList,authorization);
        return Api.OK(response);
    }

    //주문내역
    @GetMapping("/list/{storeId}")
    public Api<List<OrderResponse>> orderList(
            @PathVariable int storeId,
            @RequestParam String username
    ){
        List<OrderResponse> responses = orderService.orderList(storeId,username);
        return Api.OK(responses);
    }

}
