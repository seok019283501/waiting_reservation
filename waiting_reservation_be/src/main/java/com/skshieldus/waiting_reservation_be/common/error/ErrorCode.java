package com.skshieldus.waiting_reservation_be.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@AllArgsConstructor
@Getter
public enum ErrorCode {
    OK(200,200,"OK"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), 400,"bad request"),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 500,"server error"),
    NULL_POINT(HttpStatus.INTERNAL_SERVER_ERROR.value(), 512,"Null point"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(),401,"UNAUTHORIZED")
    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
