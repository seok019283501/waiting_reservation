package com.skshieldus.waiting_reservation_be.domain.user.dto;

import lombok.Data;

@Data
public class LoginRequest {
    String username;
    String password;
}
