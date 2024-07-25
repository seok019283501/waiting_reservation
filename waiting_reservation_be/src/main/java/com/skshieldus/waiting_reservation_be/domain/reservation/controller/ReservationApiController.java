package com.skshieldus.waiting_reservation_be.domain.reservation.controller;

import com.skshieldus.waiting_reservation_be.common.api.Api;
import com.skshieldus.waiting_reservation_be.domain.reservation.dto.ReservationRemainResponse;
import com.skshieldus.waiting_reservation_be.domain.reservation.dto.ReservationResponse;
import com.skshieldus.waiting_reservation_be.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/list/{storeId}")
    public Api<List<ReservationRemainResponse>> reservationList(@PathVariable int storeId){
        List<ReservationRemainResponse> response = reservationService.reservationList(storeId);
        return Api.OK(response);
    }
}
