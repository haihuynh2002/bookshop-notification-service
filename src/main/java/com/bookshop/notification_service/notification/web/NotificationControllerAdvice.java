package com.bookshop.notification_service.notification.web;

import com.bookshop.notification_service.notification.domain.InvalidOrderStatusException;
import com.bookshop.notification_service.notification.domain.InvalidDeliveryStatusException;
import com.bookshop.notification_service.notification.domain.EmailSendingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class NotificationControllerAdvice {

    @ExceptionHandler(InvalidOrderStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ErrorResponse> handleInvalidOrderStatusException(InvalidOrderStatusException ex) {
        return Mono.just(ErrorResponse.builder()
                .message(ex.getMessage())
                .build());
    }

    @ExceptionHandler(InvalidDeliveryStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ErrorResponse> handleInvalidDeliveryStatusException(InvalidDeliveryStatusException ex) {
        return Mono.just(ErrorResponse.builder()
                .message(ex.getMessage())
                .build());
    }

    @ExceptionHandler(EmailSendingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<ErrorResponse> handleEmailSendingException(EmailSendingException ex) {
        return Mono.just(ErrorResponse.builder()
                .message(ex.getMessage())
                .build());
    }
}