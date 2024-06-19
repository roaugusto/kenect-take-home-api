package com.kenect.contact.aggregator.service;

import com.kenect.contact.aggregator.dto.ContactDTO;

import java.util.List;

public abstract class AbstractContactService {
    public abstract List<ContactDTO> getAllContacts();
    public abstract ContactDTO getContactById(Long id);
}
