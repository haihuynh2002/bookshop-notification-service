package com.bookshop.notification_service.notification.web;

import com.bookshop.notification_service.notification.domain.SubscriberService;
import jakarta.validation.Valid;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("subscribers")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubscriberController {

    SubscriberService subscriberService;

    @PostMapping("/subscribe")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<SubscriberResponse> subscribe(@Valid @RequestBody SubscriberRequest request) {
        return subscriberService.subscribe(request);
    }

    @GetMapping
    public Flux<SubscriberResponse> getAllSubscribers() {
        return subscriberService.getAllSubscribers();
    }

    @GetMapping("/active")
    public Flux<SubscriberResponse> getActiveSubscribers() {
        return subscriberService.getActiveSubscribers();
    }

    @GetMapping("/{id}")
    public Mono<SubscriberResponse> getSubscriberById(@PathVariable Long id) {
        return subscriberService.getSubscriberById(id);
    }

    @GetMapping("/email/{email}")
    public Mono<SubscriberResponse> getSubscriberByEmail(@PathVariable String email) {
        return subscriberService.getSubscriberByEmail(email);
    }

    @PutMapping("/{id}")
    public Mono<SubscriberResponse> updateSubscriber(
            @PathVariable Long id,
            @Valid @RequestBody SubscriberRequest request) {
        return subscriberService.updateSubscriber(id, request);
    }

    @PostMapping("/{id}/unsubscribe")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> unsubscribe(@PathVariable Long id) {
        return subscriberService.unsubscribe(id);
    }

    @GetMapping("/email/{email}/unsubscribe")
    public Mono<ResponseEntity> unsubscribeByEmail(@PathVariable String email) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/unsubcribe"));
        return subscriberService.unsubscribeByEmail(email)
                .thenReturn(new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY));
    }

    @GetMapping("/{email}/subscribed")
    public Mono<Boolean> isSubscribed(@PathVariable String email) {
        return subscriberService.isSubscribed(email);
    }
}