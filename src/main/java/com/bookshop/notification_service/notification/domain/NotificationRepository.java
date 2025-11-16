package com.bookshop.notification_service.notification.domain;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface NotificationRepository extends ReactiveCrudRepository<Notification,Long> {
}
