package com.bookshop.notification_service.brevo;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@ConfigurationProperties(prefix = "brevo")
public record BrevoProperties(

	@NotNull
	URI uri,

    @NotNull
    String apiKey

){}
