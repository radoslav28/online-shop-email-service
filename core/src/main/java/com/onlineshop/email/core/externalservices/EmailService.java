package com.onlineshop.email.core.externalservices;

import com.onlineshop.email.api.exceptions.FailedEmailException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Service
public class EmailService {
    @Value("${mailgun.api.key}")
    private String apiKey;
    @Value("${mailgun.url}")
    private String url;

    public void sendEmail(String to, String text) {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("from", "onlineshop@mail.com");
        params.add("to", to);
        params.add("subject", "Order confirmation");
        params.add("text", text);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString(apiKey.getBytes()));
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new FailedEmailException();
        }
    }
}
