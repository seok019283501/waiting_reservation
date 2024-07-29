package com.skshieldus.waiting_reservation_be.domain.order.dto;

import lombok.Data;

@Data
public class OrderRequest {
    private int id;
    private int count;
}
