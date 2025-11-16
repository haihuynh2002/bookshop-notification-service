package com.bookshop.notification_service.notification.web;

import com.bookshop.notification_service.notification.domain.NewsletterService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("newsletters")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NewsletterController {

    NewsletterService newsletterService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<NewsletterResponse> createNewsletter(@Valid @RequestBody NewsletterRequest request) {
        return newsletterService.createNewsletter(request);
    }

    @GetMapping
    public Flux<NewsletterResponse> getAllNewsletters() {
        return newsletterService.getAllNewsletters();
    }

    @GetMapping("/{id}")
    public Mono<NewsletterResponse> getNewsletterById(@PathVariable Long id) {
        return newsletterService.getNewsletterById(id);
    }

    @PutMapping("/{id}")
    public Mono<NewsletterResponse> updateNewsletter(
            @PathVariable Long id,
            @Valid @RequestBody NewsletterRequest request) {
        return newsletterService.updateNewsletter(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteNewsletter(@PathVariable Long id) {
        return newsletterService.deleteNewsletter(id);
    }

//    @PostMapping("/{id}/schedule")
//    public Mono<NewsletterResponse> scheduleNewsletter(
//            @PathVariable Long id,
//            @RequestParam Instant scheduleTime) {
//        return newsletterService.scheduleNewsletter(id, scheduleTime);
//    }
}