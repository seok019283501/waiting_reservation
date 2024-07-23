package com.skshieldus.waiting_reservation_be.domain.user.service;

import com.skshieldus.waiting_reservation_be.common.role.Role;
import com.skshieldus.waiting_reservation_be.domain.user.dto.RegisterRequest;
import com.skshieldus.waiting_reservation_be.domain.user.entity.UserEntity;
import com.skshieldus.waiting_reservation_be.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    // 회원가입
    @Override
    public UserEntity register(RegisterRequest request,Role role) {
        log.debug("",request);
        UserEntity entity = new ModelMapper().map(request,UserEntity.class);
        entity.setRole(role);
        entity.setCreatedAt(LocalDateTime.now());
        //password 암호화
        entity.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        UserEntity newEntity = userRepository.save(entity);
        return newEntity;
    }
}
