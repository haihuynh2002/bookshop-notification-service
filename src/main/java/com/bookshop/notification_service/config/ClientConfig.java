package com.bookshop.notification_service.config;

import com.bookshop.notification_service.brevo.BrevoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {

	@Bean
	WebClient webClient(BrevoProperties brevoProperties, WebClient.Builder webClientBuilder) {
		return webClientBuilder
				.baseUrl(brevoProperties.uri().toString())
				.build();
	}

}
