package com.skshieldus.waiting_reservation_be.domain.reservation.service;

import com.skshieldus.waiting_reservation_be.domain.reservation.dto.ReservationRemainResponse;
import com.skshieldus.waiting_reservation_be.domain.reservation.dto.ReservationResponse;

import java.util.List;

public interface ReservationService {
    //웨이팅 예약
    ReservationResponse reservation(int storeId, String authorization);
    List<ReservationRemainResponse> reservationList(int storeId);
}
