package com.kenect.contact.aggregator.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class ApplicationProperties {

    @Value("${kenect.api.base-url}")
    private String kenectApiBaseUrl;

    @Value("${kenect.api.auth-token}")
    private String kenectApiAuthToken;

}
