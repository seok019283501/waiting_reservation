package com.skshieldus.waiting_reservation_be.domain.user.service;

import com.skshieldus.waiting_reservation_be.common.error.ErrorCode;
import com.skshieldus.waiting_reservation_be.common.exception.ApiException;
import com.skshieldus.waiting_reservation_be.common.utils.JwtUtils;
import com.skshieldus.waiting_reservation_be.db.store.StoreEntity;
import com.skshieldus.waiting_reservation_be.db.user.enums.Role;
import com.skshieldus.waiting_reservation_be.domain.user.dto.LoginRequest;
import com.skshieldus.waiting_reservation_be.domain.user.dto.LoginResponse;
import com.skshieldus.waiting_reservation_be.domain.user.dto.RegisterRequest;
import com.skshieldus.waiting_reservation_be.db.user.UserEntity;
import com.skshieldus.waiting_reservation_be.db.user.UserRepository;
import com.skshieldus.waiting_reservation_be.domain.user.dto.UserInfoResponse;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
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
        //유저 정보 확인
        UserEntity userEntity = Optional.ofNullable(userRepository.findByUsername(loginRequest.getUsername()))
                .orElseThrow(()->new ApiException(ErrorCode.BAD_REQUEST));
        //비밀번화 확인
        boolean passwordMatch = bCryptPasswordEncoder.matches(loginRequest.getPassword(), userEntity.getPassword());
        if(!passwordMatch){
            throw new ApiException(ErrorCode.BAD_REQUEST);
        }

        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtUtils.generateToken(userEntity))
                .build();
        return loginResponse;

    }

    @Override
    public UserInfoResponse info(String authorization) {
        String token = authorization.substring(7);
        String username = jwtUtils.getSubjectFromToken(token);
        UserEntity entity = Optional.ofNullable(userRepository.findByUsername(username))
                .orElseThrow(()->new ApiException(ErrorCode.BAD_REQUEST,"정보가 없습니다."));
        log.debug("user entity : ",entity);
        UserInfoResponse response = new ModelMapper().map(entity,UserInfoResponse.class);
        log.debug("user info response : ",response);
        return response;

    }


}
