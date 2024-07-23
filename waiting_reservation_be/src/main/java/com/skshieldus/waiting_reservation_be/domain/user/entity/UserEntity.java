package com.skshieldus.waiting_reservation_be.domain.user.entity;

import com.skshieldus.waiting_reservation_be.common.entity.BaseEntity;
import com.skshieldus.waiting_reservation_be.common.role.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class UserEntity extends BaseEntity {
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Column(nullable = false)
    private String birth;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;
}
