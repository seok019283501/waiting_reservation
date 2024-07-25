package com.skshieldus.waiting_reservation_be.domain.user.service;

import com.skshieldus.waiting_reservation_be.db.user.enums.Role;
import com.skshieldus.waiting_reservation_be.domain.user.dto.LoginRequest;
import com.skshieldus.waiting_reservation_be.domain.user.dto.LoginResponse;
import com.skshieldus.waiting_reservation_be.domain.user.dto.RegisterRequest;

public interface UserService {
    void register(RegisterRequest request, Role role);
    LoginResponse login(LoginRequest loginRequest);
}
