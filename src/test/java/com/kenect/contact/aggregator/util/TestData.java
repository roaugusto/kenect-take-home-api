package com.kenect.contact.aggregator.util;

import com.kenect.contact.aggregator.dto.Contact;

import java.util.Arrays;
import java.util.List;

public class TestData {

    public static final Contact CONTACT_1 = new Contact(1L, "Mrs. Willian Bradtke", "jerold@example.net", "2020-06-24T19:37:16.688Z", "2020-06-24T19:37:16.688Z");
    public static final Contact CONTACT_2 = new Contact(2L, "John Doe", "johndoe@example.net", "2021-02-10T11:10:09.987Z", "2022-05-05T15:27:17.547Z");
    public static final Contact CONTACT_3 = new Contact(3L, "jmadsen", "jmadsen@kenect.com", "2020-06-25T02:29:23.755Z", "2020-06-25T02:29:23.755Z");
    public static final Contact CONTACT_4 = new Contact(4L, "Jalisa Quigley", "clotilde.corkery@example.com", "2020-06-25T02:31:51.233Z", "2020-06-25T02:31:51.233Z");

    public static List<Contact> getContactsPage1() {
        return Arrays.asList(CONTACT_1, CONTACT_2);
    }

    public static List<Contact> getContactsPage2() {
        return Arrays.asList(CONTACT_3, CONTACT_4);
    }
}

