package com.bookshop.notification_service.notification.domain;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NewsletterSchedulerService {

    NewsletterRepository newsletterRepository;
    SubscriberRepository subscriberRepository;
    NewsletterSubscriptionRepository subscriptionRepository;
    NotificationService notificationService;

    @Scheduled(fixedRate = 60000) // Run every minute
    public void processScheduledNewsletters() {
        newsletterRepository.findScheduledNewslettersReadyToSend(Instant.now())
                .flatMap(this::sendNewsletterToSubscribers)
                .subscribe(result -> log.info("Processed newsletter: {}", result));
    }

    private Mono<Newsletter> sendNewsletterToSubscribers(Newsletter newsletter) {
        log.info("Sending newsletter: {}", newsletter.getTitle());

        return subscriberRepository.findByActiveTrue()
                .flatMap(subscriber -> sendNewsletterToSubscriber(newsletter, subscriber))
                .then(updateNewsletterStatus(newsletter));
    }

    private Mono<Void> sendNewsletterToSubscriber(Newsletter newsletter, Subscriber subscriber) {
        var notification = Notification.builder()
                .firstName(subscriber.getFirstName())
                .lastName(subscriber.getLastName())
                .subject(newsletter.getTitle())
                .email(subscriber.getEmail())
                .htmlContent(newsletter.getContent())
                .build();
        return notificationService.sendEmail(notification)
                .flatMap(response -> createSubscriptionRecord(newsletter, subscriber, true))
                .onErrorResume(error -> {
                    log.error("Failed to send newsletter to {}: {}", subscriber.getEmail(), error.getMessage());
                    return createSubscriptionRecord(newsletter, subscriber, false);
                });
    }

    private Mono<Void> createSubscriptionRecord(Newsletter newsletter, Subscriber subscriber, Boolean delivered) {
        NewsletterSubscription subscription = NewsletterSubscription.builder()
                .subscriberId(subscriber.getId())
                .newsletterId(newsletter.getId())
                .sentAt(Instant.now())
                .delivered(delivered)
                .opened(false)
                .openedAt(null)
                .build();

        return subscriptionRepository.save(subscription)
                .doOnSuccess(saved -> log.debug("Created subscription record for subscriber: {}", subscriber.getEmail()))
                .doOnError(error -> log.error("Failed to create subscription record for {}: {}",
                        subscriber.getEmail(), error.getMessage()))
                .then();
    }

    private Mono<Newsletter> updateNewsletterStatus(Newsletter newsletter) {
        newsletter.setStatus(NewsletterStatus.SENT);
        newsletter.setSentAt(Instant.now());
        return newsletterRepository.save(newsletter);
    }

    public Mono<Void> sendNewsletterImmediately(Long newsletterId) {
        return newsletterRepository.findById(newsletterId)
                .flatMap(newsletter -> {
                    log.info("Sending newsletter immediately: {}", newsletter.getTitle());
                    return sendNewsletterToSubscribers(newsletter);
                })
                .then();
    }
}