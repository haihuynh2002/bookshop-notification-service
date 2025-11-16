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
@Table("subscriber")
public class Subscriber {
    @Id
    Long id;
    String email;
    String firstName;
    String lastName;
    Boolean active;
    Instant subscribedAt;
    Instant unsubscribedAt;
}