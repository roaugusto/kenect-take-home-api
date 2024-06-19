package com.kenect.contact.aggregator.repository;

import com.kenect.contact.aggregator.config.ApplicationProperties;
import com.kenect.contact.aggregator.dto.Contact;
import com.kenect.contact.aggregator.dto.ContactResponse;
import com.kenect.contact.aggregator.util.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContactRepositoryTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ApplicationProperties applicationProperties;

    @InjectMocks
    private ContactRepository contactRepository;

    @BeforeEach
    void setUp() {
        when(applicationProperties.getKenectApiBaseUrl()).thenReturn("http://mock-api.com");
        when(applicationProperties.getKenectApiAuthToken()).thenReturn("mock-token");
    }

    @Test
    void testGetAllContacts() {
        String url = "http://mock-api.com/contacts?page=1";
        List<Contact> contactList = TestData.getContactsPage1();

        ContactResponse<List<Contact>> contactResponse = new ContactResponse<>();
        contactResponse.setData(contactList);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Current-Page", "1");
        headers.set("Total-Pages", "1");

        ResponseEntity<ContactResponse<List<Contact>>> responseEntity = new ResponseEntity<>(contactResponse, headers, HttpStatus.OK);
        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), any(HttpEntity.class), eq(new ParameterizedTypeReference<ContactResponse<List<Contact>>>() {})))
                .thenReturn(responseEntity);

        List<Contact> result = contactRepository.getAllContacts();

        assertEquals(2, result.size());
        assertEquals(TestData.CONTACT_1, result.get(0));
        assertEquals(TestData.CONTACT_2, result.get(1));
    }

    @Test
    void testGetContactById() {
        String url = "http://mock-api.com/contacts/1";
        Contact contact = TestData.CONTACT_1;

        ContactResponse<Contact> contactResponse = new ContactResponse<>();
        contactResponse.setData(contact);

        ResponseEntity<ContactResponse<Contact>> responseEntity = ResponseEntity.ok(contactResponse);
        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), any(HttpEntity.class), eq(new ParameterizedTypeReference<ContactResponse<Contact>>() {})))
                .thenReturn(responseEntity);

        Contact result = contactRepository.getContactById(1L);

        assertEquals(contact, result);
    }

    @Test
    void testNoContacts() {
        String url = "http://mock-api.com/contacts?page=1";

        ContactResponse<List<Contact>> contactResponse = new ContactResponse<>();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Current-Page", "1");
        headers.set("Total-Pages", "1");

        ResponseEntity<ContactResponse<List<Contact>>> responseEntity = new ResponseEntity<>(contactResponse, headers, HttpStatus.OK);
        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), any(HttpEntity.class), eq(new ParameterizedTypeReference<ContactResponse<List<Contact>>>() {})))
                .thenReturn(responseEntity);

        List<Contact> result = contactRepository.getAllContacts();

        assertEquals(0, result.size());
    }

    @Test
    void testMoreThanOnePage() {
        String urlPage1 = "http://mock-api.com/contacts?page=1";
        String urlPage2 = "http://mock-api.com/contacts?page=2";
        List<Contact> contactListPage1 = TestData.getContactsPage1();
        List<Contact> contactListPage2 = TestData.getContactsPage2();

        ContactResponse<List<Contact>> contactResponsePage1 = new ContactResponse<>();
        contactResponsePage1.setData(contactListPage1);

        ContactResponse<List<Contact>> contactResponsePage2 = new ContactResponse<>();
        contactResponsePage2.setData(contactListPage2);

        HttpHeaders headersPage1 = new HttpHeaders();
        headersPage1.set("Current-Page", "1");
        headersPage1.set("Total-Pages", "2");

        HttpHeaders headersPage2 = new HttpHeaders();
        headersPage2.set("Current-Page", "2");
        headersPage2.set("Total-Pages", "2");

        ResponseEntity<ContactResponse<List<Contact>>> responseEntityPage1 = new ResponseEntity<>(contactResponsePage1, headersPage1, HttpStatus.OK);
        ResponseEntity<ContactResponse<List<Contact>>> responseEntityPage2 = new ResponseEntity<>(contactResponsePage2, headersPage2, HttpStatus.OK);

        when(restTemplate.exchange(eq(urlPage1), eq(HttpMethod.GET), any(HttpEntity.class), eq(new ParameterizedTypeReference<ContactResponse<List<Contact>>>() {})))
                .thenReturn(responseEntityPage1);

        when(restTemplate.exchange(eq(urlPage2), eq(HttpMethod.GET), any(HttpEntity.class), eq(new ParameterizedTypeReference<ContactResponse<List<Contact>>>() {})))
                .thenReturn(responseEntityPage2);

        List<Contact> result = contactRepository.getAllContacts();

        assertEquals(4, result.size());
        assertEquals(TestData.CONTACT_1, result.get(0));
        assertEquals(TestData.CONTACT_2, result.get(1));
        assertEquals(TestData.CONTACT_3, result.get(2));
        assertEquals(TestData.CONTACT_4, result.get(3));
    }
}
