package com.skshieldus.waiting_reservation_be.domain.reservation.service;

import com.skshieldus.waiting_reservation_be.common.error.ErrorCode;
import com.skshieldus.waiting_reservation_be.common.exception.ApiException;
import com.skshieldus.waiting_reservation_be.common.utils.JwtUtils;
import com.skshieldus.waiting_reservation_be.db.order.OrderRepository;
import com.skshieldus.waiting_reservation_be.db.reserveration.ReservationEntity;
import com.skshieldus.waiting_reservation_be.db.reserveration.ReservationRepository;
import com.skshieldus.waiting_reservation_be.db.reserveration.enums.ReservationStatus;
import com.skshieldus.waiting_reservation_be.db.store.StoreRepository;
import com.skshieldus.waiting_reservation_be.db.store.enums.StoreStatus;
import com.skshieldus.waiting_reservation_be.db.user.UserEntity;
import com.skshieldus.waiting_reservation_be.db.user.UserRepository;
import com.skshieldus.waiting_reservation_be.domain.reservation.dto.ReservationRemainResponse;
import com.skshieldus.waiting_reservation_be.domain.reservation.dto.ReservationResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService{
    private final ReservationRepository reservationRespository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final JwtUtils jwtUtils;
    //웨이팅 예약
    @Override
    public ReservationResponse reservation(int storeId, String authorization) {
        //username & name
        String token = authorization.substring(7);
        String username = jwtUtils.getSubjectFromToken(token);
        String name = jwtUtils.getNameFromToken(token);
        //store 유무 확인
        Optional.ofNullable(storeRepository.findByIdAndStatus(storeId, StoreStatus.STATUS_ON))
                .orElseThrow(()->new ApiException(ErrorCode.BAD_REQUEST));

        //중복 예약 확인
        ReservationEntity entity = reservationRespository
                .findByUsernameAndStatus(username,ReservationStatus.STATUS_PROCESSING);
        if(entity != null){
            throw new ApiException(ErrorCode.BAD_REQUEST,"이미 예약 중 입니다.");
        }

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
        response.setName(name);
        response.setRemainingCount(size);
        return response;
    }

    //남은 인원 검색
    @Override
    public List<ReservationRemainResponse> reservationList(int storeId, String authorization) {
        //username
        String token = authorization.substring(7);
        String username = jwtUtils.getSubjectFromToken(token);
        //store 유무 확인
        Optional.ofNullable(storeRepository.findByIdAndUsernameAndStatus(storeId,username,StoreStatus.STATUS_ON))
                .orElseThrow(()->new ApiException(ErrorCode.BAD_REQUEST));

        //남은 reservation list
        List<ReservationEntity> reservationEntityList = reservationRespository.findAllByStoreIdAndStatus(
                storeId,
                ReservationStatus.STATUS_PROCESSING
        );

        List<ReservationRemainResponse> responses = reservationEntityList.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        return responses;
    }

    //웨이팅 종료
    @Override
    public void completeReservation(int storeId, int reservationId, String authorization) {
        //username
        String token = authorization.substring(7);
        String username = jwtUtils.getSubjectFromToken(token);
        //store 유무 확인
        Optional.ofNullable(storeRepository.findByIdAndUsernameAndStatus(storeId,username,StoreStatus.STATUS_ON))
                .orElseThrow(()->new ApiException(ErrorCode.BAD_REQUEST,"식당 없음"));


        ReservationEntity entity =
                Optional.ofNullable(reservationRespository.findByIdAndStoreIdAndStatus(
                        reservationId,
                        storeId,
                        ReservationStatus.STATUS_PROCESSING))
                        .orElseThrow(()->new ApiException(ErrorCode.BAD_REQUEST));

        orderRepository.deleteOrder(entity.getUsername());

        entity.setStatus(ReservationStatus.STATUS_COMPLETED);
        reservationRespository.save(entity);
    }

    //남은 웨이팅 확인
    @Override
    public ReservationResponse info(String authorization) {
        //username
        String token = authorization.substring(7);
        String username = jwtUtils.getSubjectFromToken(token);
        String name = jwtUtils.getNameFromToken(token);

        //웨이팅 확인
        ReservationEntity entity = Optional.ofNullable(reservationRespository
                        .findByUsernameAndStatus(username,ReservationStatus.STATUS_PROCESSING))
                        .orElseThrow(()->new ApiException(ErrorCode.BAD_REQUEST,"웨이팅 중이 아닙니다."));

        //식당 남은 인원 검색
        List<ReservationEntity> reservationEntityList = reservationRespository
                .findAllByStoreIdAndStatus(entity.getStoreId(),ReservationStatus.STATUS_PROCESSING);
        //user 순서까지의 수
        int count = reservationEntityList.indexOf(entity)+1;

        ReservationResponse response = new ModelMapper().map(entity,ReservationResponse.class);
        response.setName(name);
        response.setRemainingCount(count);

        return response;
    }

    @Override
    public void check(int storeId, String authorization) {
        //username
        String token = authorization.substring(7);
        String username = jwtUtils.getSubjectFromToken(token);
        String name = jwtUtils.getNameFromToken(token);

        //웨이팅 확인
        Optional.ofNullable(reservationRespository
                        .findByUsernameAndStoreIdAndStatus(username,storeId,ReservationStatus.STATUS_PROCESSING))
                .orElseThrow(()->new ApiException(ErrorCode.BAD_REQUEST,"웨이팅 중이 아닙니다."));
    }

    //response 전환
    public ReservationRemainResponse toResponse(ReservationEntity entity){

        UserEntity userEntity = userRepository.findByUsername(entity.getUsername());

        return Optional.ofNullable(entity)
                .map(it->{
                    return ReservationRemainResponse.builder()
                            .id(entity.getId())
                            .name(entity.getUsername())
                            .username(entity.getUsername())
                            .storeId(entity.getStoreId())
                            .phoneNumber(userEntity.getPhoneNumber())
                            .status(entity.getStatus())
                            .createdAt(entity.getCreatedAt())
                            .build();

                }).orElseThrow(()->new ApiException(ErrorCode.BAD_REQUEST));
    }
}
