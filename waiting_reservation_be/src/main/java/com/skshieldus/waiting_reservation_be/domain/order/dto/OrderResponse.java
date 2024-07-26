package com.skshieldus.waiting_reservation_be.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private String title;
    private String description;
    private int cost;
}
