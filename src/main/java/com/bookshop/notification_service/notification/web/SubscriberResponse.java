package com.bookshop.notification_service.notification.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubscriberResponse {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    String email;

    String firstName;
    String lastName;
    Boolean active;
}