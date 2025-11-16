package com.bookshop.notification_service.notification.domain;

import com.bookshop.notification_service.notification.web.SubscriberRequest;
import com.bookshop.notification_service.notification.web.SubscriberResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface SubscriberMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "unsubscribedAt", ignore = true)
    Subscriber toSubscriber(SubscriberRequest request);

    SubscriberResponse toSubscriberResponse(Subscriber subscriber);

    void update(@MappingTarget SubscriberRequest request, Subscriber subscriber);
}
