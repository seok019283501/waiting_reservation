package com.skshieldus.waiting_reservation_be.domain.reservation.dto;

import com.skshieldus.waiting_reservation_be.db.reserveration.enums.ReservationStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse {
    private int id;
    private String username;
    private String name;
    private int storeId;
    private ReservationStatus status;
    private int remainingCount;
    private LocalDateTime createdAt;
}
