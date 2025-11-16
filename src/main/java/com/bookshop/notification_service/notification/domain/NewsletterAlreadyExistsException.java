package com.bookshop.notification_service.notification.domain;

public class NewsletterAlreadyExistsException extends RuntimeException {
    public NewsletterAlreadyExistsException(String title, String category) {
        super("Newsletter already exists with title: " + title + " and category: " + category);
    }
}
