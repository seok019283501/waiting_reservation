package com.skshieldus.waiting_reservation_be.domain.user.controeller;


import com.skshieldus.waiting_reservation_be.common.api.Api;
import com.skshieldus.waiting_reservation_be.domain.user.dto.UserInfoResponse;
import com.skshieldus.waiting_reservation_be.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserApiContoller {
    private final UserService userService;
    @GetMapping("/info")
    public Api<UserInfoResponse> info(@RequestHeader("Authorization") String authorization){
        log.debug("Authorization : ",authorization);
        UserInfoResponse response = userService.info(authorization);
        return Api.OK(response);
    }
}
