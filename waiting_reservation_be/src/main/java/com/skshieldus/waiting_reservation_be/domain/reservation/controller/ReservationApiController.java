package com.skshieldus.waiting_reservation_be.domain.reservation.controller;

import com.skshieldus.waiting_reservation_be.common.api.Api;
import com.skshieldus.waiting_reservation_be.db.reserveration.enums.ReservationStatus;
import com.skshieldus.waiting_reservation_be.domain.reservation.dto.ReservationRemainResponse;
import com.skshieldus.waiting_reservation_be.domain.reservation.dto.ReservationResponse;
import com.skshieldus.waiting_reservation_be.domain.reservation.service.ReservationService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationApiController {
    private final ReservationService reservationService;

    //웨이팅 예약
    @PostMapping("/{storeId}")
    public Api<ReservationResponse> reservation(
            @PathVariable int storeId,
            @RequestHeader("Authorization") String authorization
    ){
        ReservationResponse response = reservationService.reservation(storeId,authorization);
        return Api.OK(response);
    }

    //해당 식당 남은 웨이팅 리스트
    @GetMapping("/list/{storeId}")
    public Api<List<ReservationRemainResponse>> reservationList(
            @PathVariable int storeId,
            @RequestHeader("Authorization") String authorization
            ){
        List<ReservationRemainResponse> response = reservationService.reservationList(storeId,authorization);
        return Api.OK(response);
    }

    //웨이팅 완료
    @PutMapping("/complete/{storeId}/{reservationId}")
    public Api<String> completeReservation(
            @PathVariable int storeId,
            @PathVariable int reservationId,
            @RequestHeader("Authorization") String authorization
    ){
        reservationService.completeReservation(storeId, reservationId, authorization);
        return Api.OK("success");
    }

    //남은 인원 확인
    @GetMapping("/remain/{storeId}")
    public Api<ReservationResponse> remain(
            @PathVariable int storeId,
            @RequestHeader("Authorization") String authorization
    ){
        ReservationResponse response = reservationService.remain(storeId,authorization);
        return Api.OK(response);
    }

}
