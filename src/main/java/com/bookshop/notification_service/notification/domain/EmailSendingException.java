package com.bookshop.notification_service.notification.domain;

public class EmailSendingException extends RuntimeException {
    public EmailSendingException(String message) {
        super("Email sending failed: " + message);
    }

    public EmailSendingException(String message, Throwable cause) {
        super("Email sending failed: " + message, cause);
    }
}
