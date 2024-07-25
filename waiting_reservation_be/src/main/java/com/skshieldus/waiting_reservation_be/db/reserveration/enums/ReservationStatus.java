package com.skshieldus.waiting_reservation_be.db.reserveration.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReservationStatus {
    STATUS_COMPLETED("완료"),
    STATUS_PROCESSING("미승인"),
    ;

    private final String description;
}
