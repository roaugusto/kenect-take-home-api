package com.kenect.contact.aggregator.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class ApplicationProperties {

    @Value("${kenect.api.base-url}")
    private String kenectApiBaseUrl;

    @Value("${kenect.api.auth-token}")
    private String kenectApiAuthToken;

    @Value("${database.hostname}")
    private String databaseHostname;

    @Value("${database.databasename}")
    private String databaseName;

    @Value("${database.username}")
    private String databaseUsername;

    @Value("${database.password}")
    private String databasePassword;
}
