package com.bookshop.notification_service.notification.web;
import com.bookshop.notification_service.notification.domain.NewsletterStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewsletterResponse {
    Long id;
    String title;
    String content;
    String category;
    NewsletterStatus status;
    Instant scheduledAt;
    Instant sentAt;
    Instant createdDate;
}
