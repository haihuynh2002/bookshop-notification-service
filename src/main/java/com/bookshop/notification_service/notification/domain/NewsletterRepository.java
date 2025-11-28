package com.bookshop.notification_service.notification.domain;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

public interface NewsletterRepository extends ReactiveCrudRepository<Newsletter, Long> {
    @Query("SELECT * FROM newsletter WHERE scheduled_at <= :now AND status = 'SCHEDULED'")
    Flux<Newsletter> findScheduledNewslettersReadyToSend(Instant now);

    Mono<Boolean> existsByTitleAndCategory(String title, String category);

    Mono<Boolean> existsByTitleAndCategoryAndIdNot(String title, String category, Long id);
}