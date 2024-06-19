package com.kenect.contact.aggregator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTO {

    private Long id;
    private String name;
    private String email;
    private String createdAt;
    private String updatedAt;

}
