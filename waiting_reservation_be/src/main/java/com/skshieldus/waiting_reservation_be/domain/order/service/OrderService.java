package com.skshieldus.waiting_reservation_be.domain.order.service;

import com.skshieldus.waiting_reservation_be.db.user.enums.Role;
import com.skshieldus.waiting_reservation_be.domain.order.dto.OrderRequest;
import com.skshieldus.waiting_reservation_be.domain.order.dto.OrderResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface OrderService {
    List<OrderResponse> order(int storeId, List<OrderRequest>  orderRequestList, String authorization);
    // 유저 본인 주문 리스트
    List<OrderResponse> orderList(int storeId, String username);

}
