package com.skshieldus.waiting_reservation_be.common.role;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Role {
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN"),
    ROLE_OWNER("OWNER"),
    ;

    private final String description;
}
