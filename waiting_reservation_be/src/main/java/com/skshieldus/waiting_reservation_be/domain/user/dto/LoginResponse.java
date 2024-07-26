package com.skshieldus.waiting_reservation_be.domain.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String name;
    private String username;
    private String token;
}
