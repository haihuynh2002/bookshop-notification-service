package com.bookshop.notification_service.notification.domain;

public class InvalidDeliveryStatusException extends RuntimeException {
    public InvalidDeliveryStatusException(String status) {
        super("Invalid delivery status for notification: " + status);
    }
}
