package com.skshieldus.waiting_reservation_be.domain.reservation.dto;

import com.skshieldus.waiting_reservation_be.db.reserveration.enums.ReservationStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReservationRemainResponse {
    private int id;
    private String username;
    private int storeId;
    private ReservationStatus status;
    private String phoneNumber;
    private String name;
    private LocalDateTime createdAt;
}
