package com.bookshop.notification_service.notification.web;

import com.bookshop.notification_service.notification.domain.SubscriberService;
import jakarta.validation.Valid;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    @PostMapping("/email/{email}/unsubscribe")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> unsubscribeByEmail(@PathVariable String email) {
        return subscriberService.unsubscribeByEmail(email);
    }

//    @GetMapping("/stats")
//    public Mono<SubscriptionStatsResponse> getSubscriptionStats() {
//        return subscriberService.getSubscriptionStats();
//    }

    @GetMapping("/{email}/subscribed")
    public Mono<Boolean> isSubscribed(@PathVariable String email) {
        return subscriberService.isSubscribed(email);
    }
}