package com.bookshop.notification_service.notification.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewsletterRequest {
    @NotBlank(message = "Title is required")
    String title;

    @NotBlank(message = "Content is required")
    String content;

    @NotBlank(message = "Category is required")
    String category;

    Instant scheduledAt;
}