package com.skshieldus.waiting_reservation_be.domain.order.service;

import com.skshieldus.waiting_reservation_be.common.error.ErrorCode;
import com.skshieldus.waiting_reservation_be.common.exception.ApiException;
import com.skshieldus.waiting_reservation_be.common.utils.JwtUtils;
import com.skshieldus.waiting_reservation_be.db.menu.MenuEntity;
import com.skshieldus.waiting_reservation_be.db.menu.MenuRepository;
import com.skshieldus.waiting_reservation_be.db.order.OrderEntity;
import com.skshieldus.waiting_reservation_be.db.order.OrderRepository;
import com.skshieldus.waiting_reservation_be.db.reserveration.ReservationRepository;
import com.skshieldus.waiting_reservation_be.db.reserveration.enums.ReservationStatus;
import com.skshieldus.waiting_reservation_be.db.store.StoreRepository;
import com.skshieldus.waiting_reservation_be.db.store.enums.StoreStatus;
import com.skshieldus.waiting_reservation_be.domain.order.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;
    private final ReservationRepository reservationRepository;
    private final JwtUtils jwtUtils;

    @Override
    public OrderResponse order(int storeId, int menuId, String authorization) {
        //username
        String token = authorization.substring(7);
        String username = jwtUtils.getSubjectFromToken(token);

        //웨이팅 예약 확인
        Optional.ofNullable(reservationRepository
                .findByUsernameAndStoreIdAndStatus(username, storeId, ReservationStatus.STATUS_PROCESSING))
                        .orElseThrow(()->new ApiException(ErrorCode.BAD_REQUEST,"예약하지 않았습니다."));

        //store 유무 확인
        Optional.ofNullable(storeRepository.findByIdAndStatus(storeId, StoreStatus.STATUS_ON))
                .orElseThrow(()->new ApiException(ErrorCode.BAD_REQUEST,"식당 없음"));

        //menu 유무 확인
        MenuEntity menuEntity = Optional.ofNullable(menuRepository.findByIdAndDeletedYn(menuId,"N"))
                .orElseThrow(()->new ApiException(ErrorCode.BAD_REQUEST,"메뉴 없음"));

        //entity생성
        OrderEntity entity = OrderEntity.builder()
                .storeId(storeId)
                .menuId(menuId)
                .username(username)
                .build();


        orderRepository.save(entity);


        OrderResponse response = new ModelMapper().map(menuEntity,OrderResponse.class);
        return response;
    }
}
