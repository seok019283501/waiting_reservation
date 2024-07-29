package com.skshieldus.waiting_reservation_be.domain.menu.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuInfoResponse {
    private int id;
    private int storeId;
    private String title;
    private String description;
    private int cost;
}
