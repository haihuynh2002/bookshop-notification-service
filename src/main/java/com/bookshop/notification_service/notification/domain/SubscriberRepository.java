package com.bookshop.notification_service.notification.domain;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SubscriberRepository extends ReactiveCrudRepository<Subscriber, Long> {
    Flux<Subscriber> findByActiveTrue();
    Mono<Subscriber> findByEmail(String email);
    @Query("UPDATE subscribers SET active = false, unsubscribed_at = NOW() WHERE email = :email")
    Mono<Integer> unsubscribeByEmail(String email);
}
