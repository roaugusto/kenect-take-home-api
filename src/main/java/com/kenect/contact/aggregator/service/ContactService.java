package com.kenect.contact.aggregator.service;

import com.kenect.contact.aggregator.dto.Contact;
import com.kenect.contact.aggregator.dto.ContactDTO;
import com.kenect.contact.aggregator.repository.ContactRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService extends AbstractContactService {

    private final ContactRepository contactRepository;
    private final ModelMapper modelMapper;

    public ContactService(ContactRepository contactRepository, ModelMapper modelMapper) {
        this.contactRepository = contactRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ContactDTO> getAllContacts() {
        List<Contact> contacts = contactRepository.getAllContacts();
        return contacts.stream()
                .map(contact -> modelMapper.map(contact, ContactDTO.class)).toList();
    }

    @Override
    public ContactDTO getContactById(Long id) {
        Contact contact = contactRepository.getContactById(id);
        return modelMapper.map(contact, ContactDTO.class);
    }
}
