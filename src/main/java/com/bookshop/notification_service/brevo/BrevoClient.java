package com.bookshop.notification_service.brevo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
public class BrevoClient {

	private static final String BREVO_ROOT_API = "/v3/smtp/email";

    @Value("${brevo.api-key}")
    private String apiKey;

	private final WebClient webClient;

	public BrevoClient(WebClient webClient) {
		this.webClient = webClient;
	}

	public Mono<EmailResponse> sendEmail(EmailRequest request) {
		return webClient
				.post()
                .uri(BREVO_ROOT_API)
                .header("api-key", apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
				.retrieve()
				.bodyToMono(EmailResponse.class)
				.timeout(Duration.ofSeconds(3), Mono.empty())
				.onErrorResume(WebClientResponseException.NotFound.class, exception -> Mono.empty())
				.retryWhen(Retry.backoff(3, Duration.ofMillis(100)))
				.onErrorResume(Exception.class, exception -> Mono.empty());
	}

}
