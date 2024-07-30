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
import com.skshieldus.waiting_reservation_be.domain.order.dto.OrderRequest;
import com.skshieldus.waiting_reservation_be.domain.order.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;
    private final ReservationRepository reservationRepository;
    private final JwtUtils jwtUtils;

    @Override
    public List<OrderResponse> order(int storeId, List<OrderRequest> orderRequestList, String authorization) {
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

        List<OrderEntity> orderEntityList =  orderRequestList.stream().map(it->{
            //entity생성
            return OrderEntity.builder()
                    .storeId(storeId)
                    .menuId(it.getId())
                    .username(username)
                    .deleteYn("N")
                    .count(it.getCount())
                    .build();
        }).collect(Collectors.toList());

        List<OrderEntity> newOrderEntityList =  orderEntityList.stream()
                .map(orderRepository::save)
                .collect(Collectors.toList());





        List<OrderResponse> response = newOrderEntityList.stream().map(this::toResponse).collect(Collectors.toList());
        return response;
    }

    @Override
    public List<OrderResponse> orderList(int storeId, String username) {

        //웨이팅 예약 확인
        Optional.ofNullable(reservationRepository
                        .findByUsernameAndStoreIdAndStatus(username, storeId, ReservationStatus.STATUS_PROCESSING))
                .orElseThrow(()->new ApiException(ErrorCode.BAD_REQUEST,"예약하지 않았습니다."));

        //store 유무 확인
        Optional.ofNullable(storeRepository.findByIdAndStatus(storeId, StoreStatus.STATUS_ON))
                .orElseThrow(()->new ApiException(ErrorCode.BAD_REQUEST,"식당 없음"));

        List<OrderEntity> orderEntity = orderRepository.findAllByUsernameAndStoreIdAndDeleteYn(username,storeId,"N");
        List<OrderResponse> response = orderEntity.stream().map(this::toResponse)
                .collect(Collectors.toList());
        return response;
    }


    public OrderResponse toResponse(OrderEntity entity){

        MenuEntity menuEntity = menuRepository.findById(entity.getMenuId());

        return OrderResponse.builder()
                .title(menuEntity.getTitle())
                .name(entity.getUsername())
                .description(menuEntity.getDescription())
                .count(entity.getCount())
                .cost(menuEntity.getCost()*entity.getCount())
                .build();
    }
}
