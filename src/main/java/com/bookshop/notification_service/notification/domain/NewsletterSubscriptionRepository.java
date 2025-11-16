package com.bookshop.notification_service.notification.domain;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface NewsletterSubscriptionRepository extends ReactiveCrudRepository<NewsletterSubscription, Long> {
}
