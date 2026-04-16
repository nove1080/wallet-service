package com.payment.wallet_service.exception.handler;

import com.payment.wallet_service.common.web.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        log.error("에러 발생 ", ex);
        return ResponseEntity.internalServerError().body(ApiResponse.with(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류가 발생했습니다."));
    }

}
