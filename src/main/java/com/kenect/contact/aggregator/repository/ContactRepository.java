package com.kenect.contact.aggregator.repository;

import com.kenect.contact.aggregator.config.ApplicationProperties;
import com.kenect.contact.aggregator.dto.Contact;
import com.kenect.contact.aggregator.dto.ContactResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ContactRepository extends AbstractContactRepository {

    private final RestTemplate restTemplate;

    public ContactRepository(RestTemplate restTemplate, ApplicationProperties applicationProperties) {
        super(applicationProperties);
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        int currentPage = 1;
        int totalPages;

        do {
            String url = String.format("%s/contacts?page=%d", getApplicationProperties().getKenectApiBaseUrl(), currentPage);
            ResponseEntity<ContactResponse<List<Contact>>> response = restTemplate.exchange(url, HttpMethod.GET, createAuthHeaders(), new ParameterizedTypeReference<ContactResponse<List<Contact>>>() {});

            Optional<ContactResponse<List<Contact>>> optionalResponse = Optional.ofNullable(response.getBody());
            optionalResponse.ifPresent(contactResponse -> {
                List<Contact> contactList = contactResponse.getData();
                if (contactList != null) {
                    contacts.addAll(contactList);
                }
            });

            HttpHeaders headers = response.getHeaders();
            String currentPageHeader = headers.getFirst("Current-Page");
            String totalPagesHeader = headers.getFirst("Total-Pages");

            currentPage = Integer.parseInt(Objects.requireNonNull(currentPageHeader));
            totalPages = Integer.parseInt(Objects.requireNonNull(totalPagesHeader));

            currentPage++;
        } while (currentPage <= totalPages);

        return contacts;
    }

    @Override
    public Contact getContactById(Long id) {
        String url = String.format("%s/contacts/%d", getApplicationProperties().getKenectApiBaseUrl(), id);
        ResponseEntity<ContactResponse<Contact>> response = restTemplate.exchange(url, HttpMethod.GET, createAuthHeaders(), new ParameterizedTypeReference<ContactResponse<Contact>>() {});
        return Objects.requireNonNull(response.getBody()).getData();
    }

}
