package com.kenect.contact.aggregator.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

@Slf4j
public class CustomResponseErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is4xxClientError()) {
            if (response.getStatusCode().value() == 404) {
                throw new NotFoundException("Resource not found");
            }
        } else if (response.getStatusCode().is5xxServerError()) {
            throw new InternalServerErrorException("Internal server error");
        }
        super.handleError(response);
    }
}
