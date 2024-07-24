package com.skshieldus.waiting_reservation_be.domain.user.service;

import com.skshieldus.waiting_reservation_be.common.error.ErrorCode;
import com.skshieldus.waiting_reservation_be.common.exception.ApiException;
import com.skshieldus.waiting_reservation_be.common.utils.JwtUtils;
import com.skshieldus.waiting_reservation_be.db.user.enums.Role;
import com.skshieldus.waiting_reservation_be.domain.user.dto.LoginRequest;
import com.skshieldus.waiting_reservation_be.domain.user.dto.LoginResponse;
import com.skshieldus.waiting_reservation_be.domain.user.dto.RegisterRequest;
import com.skshieldus.waiting_reservation_be.db.user.entity.UserEntity;
import com.skshieldus.waiting_reservation_be.db.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtUtils jwtUtils;
    // 회원가입
    @Override
    public void register(RegisterRequest request,Role role) {
        log.debug("",request);
        UserEntity entity = new ModelMapper().map(request,UserEntity.class);
        entity.setRole(role);
        entity.setCreatedAt(LocalDateTime.now());
        //password 암호화
        entity.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        userRepository.save(entity);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        UserEntity userEntity = userRepository.findByUsername(loginRequest.getUsername());
        boolean passwordMatch = bCryptPasswordEncoder.matches(loginRequest.getPassword(), userEntity.getPassword());
        if(userEntity == null || !passwordMatch){
            throw new ApiException(ErrorCode.BAD_REQUEST);
        }
        LoginResponse loginResponse = LoginResponse.builder()
                .token(setAuthentication(userEntity))
                .build();
        return loginResponse;

    }
    //authenticationManagerBuilder의 authenticate을 할 때 CustomUserDetailsService동작
    private String setAuthentication(UserEntity userEntity) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userEntity.getUsername(), userEntity.getPassword());

//        authenticationManagerBuilder.getObject()
//                .authenticate(authenticationToken);

        return jwtUtils.generateToken(userEntity);
    }
}
