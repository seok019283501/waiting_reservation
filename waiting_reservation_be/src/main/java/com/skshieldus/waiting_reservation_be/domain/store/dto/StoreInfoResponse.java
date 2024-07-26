package com.skshieldus.waiting_reservation_be.domain.store.dto;

import com.skshieldus.waiting_reservation_be.db.store.enums.StoreStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreInfoResponse {
    private int id;
    private String storeName;
    private StoreStatus status;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime openAt;
}
