package com.skshieldus.waiting_reservation_be.domain.user.service;

import com.skshieldus.waiting_reservation_be.common.role.Role;
import com.skshieldus.waiting_reservation_be.domain.user.dto.RegisterRequest;
import com.skshieldus.waiting_reservation_be.domain.user.entity.UserEntity;

public interface UserService {
    UserEntity register(RegisterRequest request, Role role);
}
