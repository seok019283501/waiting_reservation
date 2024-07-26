package com.skshieldus.waiting_reservation_be.domain.user.dto;

import com.skshieldus.waiting_reservation_be.db.user.enums.Role;
import com.skshieldus.waiting_reservation_be.db.user.enums.State;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LoginResponse {
    private int id;
    private String username;
    private String email;
    private String name;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String birth;
    private Role role;
    private State state;
    private String token;
}
