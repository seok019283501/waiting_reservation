package com.skshieldus.waiting_reservation_be.common.exception;

import com.skshieldus.waiting_reservation_be.common.error.ErrorCodeIfs;

public interface ApiExceptionItf{
    ErrorCodeIfs getErrorCodeIfs();
    String getErrorDescription();
}
