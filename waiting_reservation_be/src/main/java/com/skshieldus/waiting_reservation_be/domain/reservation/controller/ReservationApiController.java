package com.skshieldus.waiting_reservation_be.domain.reservation.controller;

import com.skshieldus.waiting_reservation_be.common.api.Api;
import com.skshieldus.waiting_reservation_be.domain.reservation.dto.ReservationResponse;
import com.skshieldus.waiting_reservation_be.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationApiController {
    private final ReservationService reservationService;

    //웨이팅 예약
    @PostMapping("/{storeId}")
    public Api<ReservationResponse> reservation(@PathVariable int storeId, @RequestHeader("Authorization") String authorization){
        ReservationResponse response = reservationService.reservation(storeId,authorization);
        return Api.OK(response);
    }
}
