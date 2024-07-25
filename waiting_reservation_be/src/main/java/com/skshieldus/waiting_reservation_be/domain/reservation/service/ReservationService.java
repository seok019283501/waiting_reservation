package com.skshieldus.waiting_reservation_be.domain.reservation.service;

import com.skshieldus.waiting_reservation_be.domain.reservation.dto.ReservationResponse;

public interface ReservationService {
    //웨이팅 예약
    ReservationResponse reservation(int storeId, String authorization);
}
