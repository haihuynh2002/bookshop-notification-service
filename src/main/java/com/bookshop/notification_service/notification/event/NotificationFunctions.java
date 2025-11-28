package com.bookshop.notification_service.notification.event;

import com.bookshop.notification_service.notification.domain.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class NotificationFunctions {

    @Bean
    public Consumer<Flux<OrderEvent>> handleOrderEvent(NotificationService notificationService) {
        return flux -> notificationService.consumeOrderEvent(flux)
                .doOnNext(notification -> log.info("The notification with id {} is created", notification.getId()))
                .subscribe();
    }

    @Bean
    public Consumer<Flux<DeliveryEvent>> handleDeliveryEvent(NotificationService notificationService) {
        return flux -> notificationService.consumeDeliveryEvent(flux)
                .doOnNext(notification -> log.info("The notification with id {} is created", notification.getId()))
                .subscribe();
    }
}
