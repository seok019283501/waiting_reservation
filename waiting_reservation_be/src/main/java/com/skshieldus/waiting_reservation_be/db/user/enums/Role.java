package com.skshieldus.waiting_reservation_be.db.user.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Role {
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN"),
    ROLE_OWNER("OWNER"),
    ;

    private final String description;
}
