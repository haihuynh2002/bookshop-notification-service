package com.bookshop.notification_service.notification.domain;

import com.bookshop.notification_service.notification.event.DeliveryEvent;
import com.bookshop.notification_service.notification.event.ExchangeEvent;
import com.bookshop.notification_service.notification.event.OrderEvent;
import com.bookshop.notification_service.notification.event.PaymentEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface NotificationMapper {
    @Mapping(target = "id", ignore = true)
    Notification toNotification(OrderEvent orderEvent);

    @Mapping(target = "id", ignore = true)
    Notification toNotification(ExchangeEvent exchangeEvent);

    @Mapping(target = "id", ignore = true)
    Notification toNotification(PaymentEvent paymentEvent);

    @Mapping(target = "id", ignore = true)
    Notification toNotification(DeliveryEvent deliveryEvent);
}
