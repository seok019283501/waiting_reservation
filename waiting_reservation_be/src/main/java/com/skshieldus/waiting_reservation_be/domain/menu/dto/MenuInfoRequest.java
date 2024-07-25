package com.skshieldus.waiting_reservation_be.domain.menu.dto;

import lombok.Data;

@Data
public class MenuInfoRequest {
    private String title;
    private String description;
    private int cost;
}
