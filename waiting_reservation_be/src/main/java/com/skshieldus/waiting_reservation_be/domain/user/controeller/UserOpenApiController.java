package com.skshieldus.waiting_reservation_be.domain.user.controeller;

import com.skshieldus.waiting_reservation_be.common.api.Api;
import com.skshieldus.waiting_reservation_be.db.user.enums.Role;
import com.skshieldus.waiting_reservation_be.domain.user.dto.LoginRequest;
import com.skshieldus.waiting_reservation_be.domain.user.dto.LoginResponse;
import com.skshieldus.waiting_reservation_be.domain.user.dto.RegisterRequest;
import com.skshieldus.waiting_reservation_be.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/user")
@Slf4j
public class UserOpenApiController {
    private final UserService userService;

    //로그인
    @PostMapping("/login")
    public Api<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        log.debug("",loginRequest);
        LoginResponse response = userService.login(loginRequest);
        return Api.OK(response);
    }


    //회원가입
    @PostMapping("/register/{role}")
    public Api<String> register(
            @RequestBody RegisterRequest request,
            @PathVariable(name = "role") Role role
    ){
        userService.register(request,role);
        return Api.OK("success");
    }
}
