package com.skshieldus.waiting_reservation_be.domain.reservation.service;

import com.skshieldus.waiting_reservation_be.common.error.ErrorCode;
import com.skshieldus.waiting_reservation_be.common.exception.ApiException;
import com.skshieldus.waiting_reservation_be.common.utils.JwtUtils;
import com.skshieldus.waiting_reservation_be.db.reserveration.ReservationEntity;
import com.skshieldus.waiting_reservation_be.db.reserveration.ReservationRepository;
import com.skshieldus.waiting_reservation_be.db.reserveration.enums.ReservationStatus;
import com.skshieldus.waiting_reservation_be.db.store.StoreEntity;
import com.skshieldus.waiting_reservation_be.db.store.StoreRepository;
import com.skshieldus.waiting_reservation_be.domain.reservation.dto.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService{
    private final ReservationRepository reservationRespository;
    private final StoreRepository storeRepository;
    private final JwtUtils jwtUtils;
    //웨이팅 예약
    @Override
    public ReservationResponse reservation(int storeId, String authorization) {
        //store 유무 확인
        Optional.ofNullable(storeRepository.findById(storeId))
                .orElseThrow(()->new ApiException(ErrorCode.BAD_REQUEST));
        //username
        String token = authorization.substring(7);
        String username = jwtUtils.getSubjectFromToken(token);
        //예약 수
        List<ReservationEntity> reservationEntityList = reservationRespository.findAllByStoreIdAndStatus(storeId, ReservationStatus.STATUS_PROCESSING);
        int size = reservationEntityList.size()+1;

        //entity 저장
        ReservationEntity reservationEntity = ReservationEntity.builder()
                .username(username)
                .storeId(storeId)
                .status(ReservationStatus.STATUS_PROCESSING)
                .createdAt(LocalDateTime.now())
                .build();
        ReservationEntity newEntity = reservationRespository.save(reservationEntity);


        ReservationResponse response = new ModelMapper().map(newEntity,ReservationResponse.class);
        response.setRemainingCount(size);
        return response;
    }
}
