package com.skshieldus.waiting_reservation_be.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private String title;
    private String name;
    private String description;
    private int count;
    private int cost;
}
