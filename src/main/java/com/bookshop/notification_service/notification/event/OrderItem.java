package com.bookshop.notification_service.notification.event;


import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class OrderItem {
    @Id
    Long id;
    Long orderId;
    Long bookId;

    String isbn;
    String title;
    String author;
    String publisher;
    Double price;
    Integer quantity;

    Long typeId;
    String typeName;
}
