package com.skshieldus.waiting_reservation_be.domain.store.dto;

import com.skshieldus.waiting_reservation_be.db.store.enums.StoreStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class StoreDetailInfoResponse {
    private int id;
    private String storeName;
    private StoreStatus status;
    private String username;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime openAt;
}
