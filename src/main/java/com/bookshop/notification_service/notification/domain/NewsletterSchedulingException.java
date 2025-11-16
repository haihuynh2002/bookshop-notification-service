package com.bookshop.notification_service.notification.domain;

public class NewsletterSchedulingException extends RuntimeException {
    public NewsletterSchedulingException(String message) {
        super("Newsletter scheduling failed: " + message);
    }

    public NewsletterSchedulingException(String message, Throwable cause) {
        super("Newsletter scheduling failed: " + message, cause);
    }
}