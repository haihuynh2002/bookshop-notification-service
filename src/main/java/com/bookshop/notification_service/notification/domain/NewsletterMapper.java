package com.bookshop.notification_service.notification.domain;

import com.bookshop.notification_service.notification.web.NewsletterRequest;
import com.bookshop.notification_service.notification.web.NewsletterResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface NewsletterMapper {
    @Mapping(target = "sentAt", ignore = true)
    Newsletter toNewsletter(NewsletterRequest request);

    NewsletterResponse toNewsletterResponse(Newsletter newsletter);

    void update(@MappingTarget Newsletter newsletter, NewsletterRequest request);
}
