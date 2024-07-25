package com.skshieldus.waiting_reservation_be.domain.user.service;

import com.skshieldus.waiting_reservation_be.common.error.ErrorCode;
import com.skshieldus.waiting_reservation_be.common.exception.ApiException;
import com.skshieldus.waiting_reservation_be.db.user.UserEntity;
import com.skshieldus.waiting_reservation_be.db.user.UserRepository;
import com.skshieldus.waiting_reservation_be.domain.user.dto.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
//UserDetailsService : Spring Security에서 유저의 정보를 가져오는 인터페이스
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    //회원 정보 찾기
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity entity = Optional.ofNullable(userRepository.findByUsername(username))
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));
        log.debug("", entity);

        if(entity == null){
            throw new ApiException(ErrorCode.BAD_REQUEST,"등록된 사용자 정보가 없습니다.");
        }
        return new CustomUserDetail(entity);
    }
}
