package com.bookshop.notification_service.notification.domain;

public class NewsletterNotFoundException extends RuntimeException {
    public NewsletterNotFoundException(Long id) {
        super("Newsletter not found with id: " + id);
    }
}
