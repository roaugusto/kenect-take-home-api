package com.kenect.contact.aggregator.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Data;

@Data
public class ContactResponse<T> {
    private T data;

    @JsonAnySetter
    public void setDynamicField(String name, T value) {
        this.data = value;
    }
}
