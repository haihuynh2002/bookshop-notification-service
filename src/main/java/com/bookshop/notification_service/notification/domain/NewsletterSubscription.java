package com.bookshop.notification_service.notification.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table("newsletter_subscription")
public class NewsletterSubscription {
    @Id
    Long id;
    Long subscriberId;
    Long newsletterId;
    Instant sentAt;
    Boolean delivered;
    Boolean opened;
    Instant openedAt;
}