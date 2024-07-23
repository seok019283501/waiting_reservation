package com.skshieldus.waiting_reservation_be.handler;

import com.skshieldus.waiting_reservation_be.common.api.Api;
import com.skshieldus.waiting_reservation_be.common.error.ErrorCodeIfs;
import com.skshieldus.waiting_reservation_be.common.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Order(value = Integer.MIN_VALUE)
public class ApiExceptionHandler {
    @ExceptionHandler(value = ApiException.class)
    public Api<Object> apiResponseEntity(ApiException apiException){
        log.debug("", apiException);
        ErrorCodeIfs errorCode = apiException.getErrorCodeIfs();
        return Api.ERROR(errorCode,apiException.getErrorDescription());
    }
}
