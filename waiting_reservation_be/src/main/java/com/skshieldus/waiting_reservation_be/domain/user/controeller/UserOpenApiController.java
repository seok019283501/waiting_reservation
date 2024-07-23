package com.skshieldus.waiting_reservation_be.domain.user.controeller;

import com.skshieldus.waiting_reservation_be.common.api.Api;
import com.skshieldus.waiting_reservation_be.common.role.Role;
import com.skshieldus.waiting_reservation_be.domain.user.dto.RegisterRequest;
import com.skshieldus.waiting_reservation_be.domain.user.entity.UserEntity;
import com.skshieldus.waiting_reservation_be.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/user")
public class UserOpenApiController {
    private final UserService userService;


    @PostMapping("/register/{role}")
    public Api<String> register(
            @RequestBody RegisterRequest request,
            @PathVariable(name = "role") Role role
    ){
        UserEntity entity = userService.register(request,role);
        return Api.OK("success");
    }
}
