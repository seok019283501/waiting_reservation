package com.skshieldus.waiting_reservation_be.db.user.enums;

import lombok.AllArgsConstructor;

//admin 승인 미승인
@AllArgsConstructor
public enum State {
    STATE_ON("admin 승인"),
    STATE_OFF("admin 미승인"),
    ;

    private final String description;
}
