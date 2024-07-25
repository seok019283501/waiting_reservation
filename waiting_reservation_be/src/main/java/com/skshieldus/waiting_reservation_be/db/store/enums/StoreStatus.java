package com.skshieldus.waiting_reservation_be.db.store.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StoreStatus {
    STATUS_ON("승인"),
    STATUS_OFF("미승인"),
            ;

    private final String description;
}
