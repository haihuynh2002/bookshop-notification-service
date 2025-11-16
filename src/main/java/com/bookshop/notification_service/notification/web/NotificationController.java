package com.bookshop.notification_service.notification.web;

import com.bookshop.notification_service.notification.domain.Notification;
import com.bookshop.notification_service.notification.domain.NotificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public Flux<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }
}
