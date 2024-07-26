package com.skshieldus.waiting_reservation_be.domain.user.dto;

import com.skshieldus.waiting_reservation_be.db.user.enums.Role;
import com.skshieldus.waiting_reservation_be.db.user.enums.State;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoResponse {
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
}
