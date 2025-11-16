package com.bookshop.notification_service.notification.domain;


import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Table(name = "notification")
public class Notification {
    @Id
    Long id;
    String userId;
    String messageId;

    String firstName;
    String lastName;
    String email;

    String subject;
    String htmlContent;
}
