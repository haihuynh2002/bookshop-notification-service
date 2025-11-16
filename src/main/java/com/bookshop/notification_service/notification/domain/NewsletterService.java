package com.bookshop.notification_service.notification.domain;

import com.bookshop.notification_service.notification.web.NewsletterRequest;
import com.bookshop.notification_service.notification.web.NewsletterResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NewsletterService {

    NewsletterRepository newsletterRepository;
    NewsletterMapper newsletterMapper;

    public Mono<NewsletterResponse> createNewsletter(NewsletterRequest request) {
        return validateNewsletterUniqueness(request)
                .then(Mono.defer(() -> createAndSaveNewsletter(request)))
                .map(newsletterMapper::toNewsletterResponse);
    }

    public Flux<NewsletterResponse> getAllNewsletters() {
        return newsletterRepository.findAll()
                .map(newsletterMapper::toNewsletterResponse);
    }

    public Mono<NewsletterResponse> getNewsletterById(Long id) {
        return newsletterRepository.findById(id)
                .switchIfEmpty(Mono.error(new NewsletterNotFoundException(id)))
                .map(newsletterMapper::toNewsletterResponse);
    }

    public Mono<NewsletterResponse> updateNewsletter(Long id, NewsletterRequest request) {
        log.info("Updating newsletter with id: {}, request: {}", id, request);
        return newsletterRepository.findById(id)
                .switchIfEmpty(Mono.error(new NewsletterNotFoundException(id)))
                .flatMap(existing -> validateUpdateUniqueness(existing, request))
                .map(existing -> {
                    newsletterMapper.update(existing, request);
                    return existing;
                })
                .flatMap(newsletterRepository::save)
                .map(newsletterMapper::toNewsletterResponse);
    }

    public Mono<Void> deleteNewsletter(Long id) {
        return newsletterRepository.existsById(id)
                .flatMap(exists -> {
                    if (!exists) {
                        return Mono.error(new NewsletterNotFoundException(id));
                    }
                    return newsletterRepository.deleteById(id);
                });
    }

    public Mono<NewsletterResponse> scheduleNewsletter(Long id, Instant scheduleTime) {
        return newsletterRepository.findById(id)
                .switchIfEmpty(Mono.error(new NewsletterNotFoundException(id)))
                .map(newsletter -> scheduleNewsletter(newsletter, scheduleTime))
                .flatMap(newsletterRepository::save)
                .map(newsletterMapper::toNewsletterResponse);
    }

    private Mono<Void> validateNewsletterUniqueness(NewsletterRequest request) {
        return newsletterRepository.existsByTitleAndCategory(request.getTitle(), request.getCategory())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new NewsletterAlreadyExistsException(request.getTitle(), request.getCategory()));
                    }
                    return Mono.empty();
                });
    }

    private Mono<Newsletter> createAndSaveNewsletter(NewsletterRequest request) {
        return Mono.fromCallable(() -> {
            Newsletter newsletter = newsletterMapper.toNewsletter(request);
            if (request.getScheduledAt() != null && request.getScheduledAt().isAfter(Instant.now())) {
                newsletter.setStatus(NewsletterStatus.SCHEDULED);
            }
            return newsletter;
        }).flatMap(newsletterRepository::save);
    }

    private Mono<Newsletter> validateUpdateUniqueness(Newsletter existing, NewsletterRequest request) {
        if (!existing.getTitle().equals(request.getTitle()) || !existing.getCategory().equals(request.getCategory())) {
            return newsletterRepository.existsByTitleAndCategoryAndIdNot(request.getTitle(), request.getCategory(), existing.getId())
                    .flatMap(exists -> {
                        if (exists) {
                            return Mono.error(new NewsletterAlreadyExistsException(request.getTitle(), request.getCategory()));
                        }
                        return Mono.just(existing);
                    });
        }
        return Mono.just(existing);
    }

    private Newsletter scheduleNewsletter(Newsletter newsletter, Instant scheduleTime) {
        newsletter.setStatus(NewsletterStatus.SCHEDULED);
        newsletter.setScheduledAt(scheduleTime);
        return newsletter;
    }
}