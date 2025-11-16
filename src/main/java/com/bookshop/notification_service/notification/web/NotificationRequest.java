package com.bookshop.notification_service.notification.web;

public record NotificationRequest (
        String username,
        String subject,
        String htmlContent
) {
}
