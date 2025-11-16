package com.bookshop.notification_service.notification.domain;

import com.bookshop.notification_service.notification.web.SubscriberRequest;
import com.bookshop.notification_service.notification.web.SubscriberResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriberService {

    private final SubscriberRepository subscriberRepository;
    private final SubscriberMapper subscriberMapper;

    public Mono<SubscriberResponse> subscribe(SubscriberRequest request) {
        return subscriberRepository.findByEmail(request.getEmail())
                .flatMap(existing -> {
                    if (existing.getActive()) {
                        return Mono.error(new RuntimeException("Email already subscribed"));
                    } else {
                        existing.setActive(true);
                        existing.setUnsubscribedAt(null);
                        return subscriberRepository.save(existing)
                                .map(subscriberMapper::toSubscriberResponse);
                    }
                })
                .switchIfEmpty(
                        Mono.defer(() -> {
                            Subscriber newSubscriber = subscriberMapper.toSubscriber(request);
                            return subscriberRepository.save(newSubscriber)
                                    .map(subscriberMapper::toSubscriberResponse);
                        })
                );
    }

    public Mono<SubscriberResponse> getSubscriberById(Long id) {
        return subscriberRepository.findById(id)
                .map(subscriberMapper::toSubscriberResponse);
    }

    public Mono<SubscriberResponse> getSubscriberByEmail(String email) {
        return subscriberRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new RuntimeException("Subcriber doesn't exist")))
                .map(subscriberMapper::toSubscriberResponse);
    }

    public Flux<SubscriberResponse> getAllSubscribers() {
        return subscriberRepository.findAll()
                .map(subscriberMapper::toSubscriberResponse);
    }

    public Flux<SubscriberResponse> getActiveSubscribers() {
        return subscriberRepository.findByActiveTrue()
                .map(subscriberMapper::toSubscriberResponse);
    }

    public Mono<SubscriberResponse> updateSubscriber(Long id, SubscriberRequest request) {
        return subscriberRepository.findById(id)
                .flatMap(existing -> {
                    subscriberMapper.update(request, existing);
                    return subscriberRepository.save(existing)
                            .map(subscriberMapper::toSubscriberResponse);
                });
    }

    public Mono<Void> unsubscribe(Long id) {
        return subscriberRepository.findById(id)
                .flatMap(subscriber -> {
                    subscriber.setActive(false);
                    subscriber.setUnsubscribedAt(Instant.now());
                    return subscriberRepository.save(subscriber)
                            .then();
                });
    }

    public Mono<Void> unsubscribeByEmail(String email) {
        return subscriberRepository.unsubscribeByEmail(email)
                .flatMap(result -> {
                    if (result > 0) {
                        log.info("Unsubscribed email: {}", email);
                        return Mono.empty();
                    } else {
                        return Mono.error(new RuntimeException("Subscriber not found"));
                    }
                });
    }

//    public Mono<SubscriptionStatsResponse> getSubscriptionStats() {
//        Mono<Long> totalSubscribers = subscriberRepository.count();
//        Mono<Long> activeSubscribers = subscriberRepository.findByActiveTrue().count();
//
//        return Mono.zip(totalSubscribers, activeSubscribers)
//                .map(tuple -> SubscriptionStatsResponse.builder()
//                        .totalSubscribers(tuple.getT1())
//                        .activeSubscribers(tuple.getT2())
//                        .inactiveSubscribers(tuple.getT1() - tuple.getT2())
//                        .build());
//    }

    public Mono<Boolean> isSubscribed(String email) {
        return subscriberRepository.findByEmail(email)
                .map(Subscriber::getActive)
                .defaultIfEmpty(false);
    }
}