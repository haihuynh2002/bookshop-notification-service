package com.bookshop.notification_service.notification.domain;

import com.bookshop.notification_service.brevo.BrevoClient;
import com.bookshop.notification_service.brevo.EmailRequest;
import com.bookshop.notification_service.brevo.EmailResponse;
import com.bookshop.notification_service.notification.event.DeliveryEvent;
import com.bookshop.notification_service.notification.event.DeliveryStatus;
import com.bookshop.notification_service.notification.event.OrderEvent;
import com.bookshop.notification_service.brevo.Recipient;
import com.bookshop.notification_service.brevo.Sender;
import com.bookshop.notification_service.notification.event.OrderStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.management.monitor.MonitorNotification;
import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NotificationService {
    NotificationRepository notificationRepository;
    NotificationMapper notificationMapper;
    BrevoClient brevoClient;

    @Transactional
    public Flux<Notification> consumeOrderEvent(Flux<OrderEvent> flux) {
        return flux
                .flatMap(event -> {
                    return switch(event.getStatus()) {
                        case OrderStatus.ACCEPTED -> sendOrderAcceptedEmail(event);
                        case OrderStatus.CANCELLED -> sendOrderCancelledEmail(event);
                        default -> Mono.empty();
                    };
                })
                .flatMap(this::sendEmail)
                .flatMap(notificationRepository::save);

    }

    private Mono<Notification> sendOrderCancelledEmail(OrderEvent event) {
        return Mono.fromCallable(() -> {
            var notification = notificationMapper.toNotification(event);
            notification.setSubject("Order Cancelled");
            notification.setHtmlContent("Your order with id: " + event.getId() + " is cancelled");
            return notification;
        });
    }

    private Mono<Notification> sendOrderAcceptedEmail(OrderEvent event) {
        return Mono.fromCallable(() -> {
            var notification = notificationMapper.toNotification(event);
            notification.setSubject("Order Accepted");
            notification.setHtmlContent("Your order with id: " + event.getId() + " is accepted");
            return notification;
        });
    }

    public Flux<Notification> consumeDeliveryEvent(Flux<DeliveryEvent> flux) {
        return flux
                .flatMap(event -> {
                    return switch(event.getStatus()) {
                        case DeliveryStatus.SHIPPED -> sendOrderShippedEmail(event);
                        case DeliveryStatus.CANCELLED -> sendOrderCancelledEmail(event);
                        default -> Mono.empty();
                    };
                })
                .flatMap(this::sendEmail)
                .flatMap(notificationRepository::save);
    }

    private Mono<Notification> sendOrderShippedEmail(DeliveryEvent event) {
        return Mono.fromCallable(() -> {
            var notification = notificationMapper.toNotification(event);
            notification.setSubject("Order Shipped");
            notification.setHtmlContent("Your order with id: " + event.getId() + " is shipped");
            return notification;
        });
    }

    private Mono<Notification> sendOrderCancelledEmail(DeliveryEvent event) {
        return Mono.fromCallable(() -> {
            var notification = notificationMapper.toNotification(event);
            notification.setSubject("Order Cancelled");
            notification.setHtmlContent("Your order with id: " + event.getId() + " is cancelled");
            return notification;
        });
    }


    public Mono<Notification> sendEmail(Notification notification) {
        Sender sender = Sender.builder()
                .name("Bookshop")
                .email("info.haihuynh@gmail.com")
                .build();
        List<Recipient> recipients = List.of(Recipient.builder()
                        .name(notification.getFirstName() + " " + notification.getLastName())
                        .email(notification.getEmail())
                .build());
        EmailRequest request = EmailRequest.builder()
                .sender(sender)
                .to(recipients)
                .subject(notification.getSubject())
                .htmlContent(notification.getHtmlContent())
                .build();
        return brevoClient.sendEmail(request)
                .map(response -> {
                    notification.setMessageId(response.getMessageId());
                    return notification;
                })
                .doOnNext(response -> log.info("Email sent successfully. Message ID: {}", response.getMessageId()))
                .onErrorResume(error -> {
                    log.error("Failed to send email for notification: {}", notification.getId(), error);
                    return Mono.just(notification);
                });
    }

    public Flux<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }
}
