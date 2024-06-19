package com.kenect.contact.aggregator.repository;

import com.kenect.contact.aggregator.config.ApplicationProperties;
import com.kenect.contact.aggregator.dto.Contact;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.util.List;

public abstract class AbstractContactRepository {

    private final ApplicationProperties applicationProperties;

    protected AbstractContactRepository(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public abstract List<Contact> getAllContacts();
    public abstract Contact getContactById(Long id);

    protected HttpEntity<String> createAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + applicationProperties.getKenectApiAuthToken());
        return new HttpEntity<>(headers);
    }

    protected ApplicationProperties getApplicationProperties() {
        return this.applicationProperties;
    }
}
