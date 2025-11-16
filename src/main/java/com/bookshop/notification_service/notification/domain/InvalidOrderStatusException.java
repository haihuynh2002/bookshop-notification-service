package com.bookshop.notification_service.notification.domain;

public class InvalidOrderStatusException extends RuntimeException {
    public InvalidOrderStatusException(String status) {
        super("Invalid order status for notification: " + status);
    }
}
