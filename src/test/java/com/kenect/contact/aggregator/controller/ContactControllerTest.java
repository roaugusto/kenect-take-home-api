package com.kenect.contact.aggregator.controller;

import com.kenect.contact.aggregator.dto.ContactDTO;
import com.kenect.contact.aggregator.service.ContactService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ContactController.class)
@ContextConfiguration(classes = {ContactController.class, ContactService.class})
class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService contactService;

    @InjectMocks
    private ContactController contactController;

    private final ContactDTO contactDTO1 = new ContactDTO(1L, "Mrs. Willian Bradtke", "jerold@example.net", "2020-06-24T19:37:16.688Z", "2020-06-24T19:37:16.688Z");;
    private final ContactDTO contactDTO2 = new ContactDTO(2L, "John Doe", "johndoe@example.net", "2021-02-10T11:10:09.987Z", "2022-05-05T15:27:17.547Z");


    @Test
    void testGetAllContacts() throws Exception {
        when(contactService.getAllContacts()).thenReturn(List.of(contactDTO1, contactDTO2));

        mockMvc.perform(get("/contacts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"name\":\"Mrs. Willian Bradtke\",\"email\":\"jerold@example.net\",\"createdAt\":\"2020-06-24T19:37:16.688Z\",\"updatedAt\":\"2020-06-24T19:37:16.688Z\"}," +
                        "{\"id\":2,\"name\":\"John Doe\",\"email\":\"johndoe@example.net\",\"createdAt\":\"2021-02-10T11:10:09.987Z\",\"updatedAt\":\"2022-05-05T15:27:17.547Z\"}]"));
    }

    @Test
    void testGetContactById() throws Exception {
        when(contactService.getContactById(anyLong())).thenReturn(contactDTO1);

        mockMvc.perform(get("/contacts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"Mrs. Willian Bradtke\",\"email\":\"jerold@example.net\",\"createdAt\":\"2020-06-24T19:37:16.688Z\",\"updatedAt\":\"2020-06-24T19:37:16.688Z\"}"));
    }
}
