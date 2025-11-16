package com.bookshop.notification_service.notification.web;

import com.bookshop.notification_service.notification.domain.NewsletterNotFoundException;
import com.bookshop.notification_service.notification.domain.NewsletterAlreadyExistsException;
import com.bookshop.notification_service.notification.domain.NewsletterSchedulingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class NewsletterControllerAdvice {

    @ExceptionHandler(NewsletterNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ErrorResponse> handleNewsletterNotFoundException(NewsletterNotFoundException ex) {
        return Mono.just(ErrorResponse.builder()
                .message(ex.getMessage())
                .build());
    }

    @ExceptionHandler(NewsletterAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Mono<ErrorResponse> handleNewsletterAlreadyExistsException(NewsletterAlreadyExistsException ex) {
        return Mono.just(ErrorResponse.builder()
                .message(ex.getMessage())
                .build());
    }

    @ExceptionHandler(NewsletterSchedulingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ErrorResponse> handleNewsletterSchedulingException(NewsletterSchedulingException ex) {
        return Mono.just(ErrorResponse.builder()
                .message(ex.getMessage())
                .build());
    }
}
