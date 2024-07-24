package com.skshieldus.waiting_reservation_be.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String name;
    private String phoneNumber;
    private String birth;
}
