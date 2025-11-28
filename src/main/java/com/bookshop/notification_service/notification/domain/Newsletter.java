package com.bookshop.notification_service.notification.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table("newsletter")
public class Newsletter {
    @Id
    Long id;
    String title;
    String content;
    NewsLetterCategory category;
    NewsletterStatus status;
    Instant scheduledAt;
    Instant sentAt;

    @CreatedDate
    Instant createdDate;

    @LastModifiedDate
    Instant lastModifiedDate;

    @CreatedBy
    String createdBy;

    @LastModifiedBy
    String lastModifiedBy;

    @Version
    int version;
}