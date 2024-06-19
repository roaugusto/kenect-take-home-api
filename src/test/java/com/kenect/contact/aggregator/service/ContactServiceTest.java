package com.kenect.contact.aggregator.service;

import com.kenect.contact.aggregator.dto.ContactDTO;
import com.kenect.contact.aggregator.repository.ContactRepository;
import com.kenect.contact.aggregator.util.TestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ContactService contactService;

    private final ContactDTO contactDTO1 = new ContactDTO(1L, "Mrs. Willian Bradtke", "jerold@example.net", "2020-06-24T19:37:16.688Z", "2020-06-24T19:37:16.688Z");;
    private final ContactDTO contactDTO2 = new ContactDTO(2L, "John Doe", "johndoe@example.net", "2021-02-10T11:10:09.987Z", "2022-05-05T15:27:17.547Z");

    @Test
    void testGetAllContacts() {
        when(contactRepository.getAllContacts()).thenReturn(TestData.getContactsPage1());
        when(modelMapper.map(TestData.CONTACT_1, ContactDTO.class)).thenReturn(contactDTO1);
        when(modelMapper.map(TestData.CONTACT_2, ContactDTO.class)).thenReturn(contactDTO2);

        List<ContactDTO> result = contactService.getAllContacts();

        assertEquals(2, result.size());
        assertEquals(contactDTO1, result.get(0));
        assertEquals(contactDTO2, result.get(1));
    }

    @Test
    void testGetContactById() {
        when(contactRepository.getContactById(anyLong())).thenReturn(TestData.CONTACT_1);
        when(modelMapper.map(TestData.CONTACT_1, ContactDTO.class)).thenReturn(contactDTO1);

        ContactDTO result = contactService.getContactById(1L);

        assertEquals(contactDTO1, result);
    }

}
