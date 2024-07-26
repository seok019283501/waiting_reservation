package com.skshieldus.waiting_reservation_be.domain.user.service;

import com.skshieldus.waiting_reservation_be.db.user.enums.Role;
import com.skshieldus.waiting_reservation_be.domain.user.dto.LoginRequest;
import com.skshieldus.waiting_reservation_be.domain.user.dto.LoginResponse;
import com.skshieldus.waiting_reservation_be.domain.user.dto.RegisterRequest;
import com.skshieldus.waiting_reservation_be.domain.user.dto.UserInfoResponse;

public interface UserService {
    //회원 가입
    void register(RegisterRequest request, Role role);
    //로그인
    LoginResponse login(LoginRequest loginRequest);
    //회원정보
    UserInfoResponse info(String authorization);
}
