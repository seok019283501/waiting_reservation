package com.skshieldus.waiting_reservation_be.domain.order.service;

import com.skshieldus.waiting_reservation_be.domain.order.dto.OrderResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

public interface OrderService {
    OrderResponse order(int storeId, int menuId, String authorization);
}
