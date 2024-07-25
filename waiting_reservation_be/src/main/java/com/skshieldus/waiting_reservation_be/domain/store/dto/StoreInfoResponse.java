package com.skshieldus.waiting_reservation_be.domain.store.dto;

import com.skshieldus.waiting_reservation_be.db.store.enums.StoreStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StoreInfoResponse {
    private String id;
    private String storeName;
    private StoreStatus status;
    private String username;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime openAt;
    private String businessRegistrationUrl;
}
