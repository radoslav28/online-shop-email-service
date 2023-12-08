package com.onlineshop.email.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineshop.email.api.exceptions.InvalidDataException;
import com.onlineshop.email.core.externalservices.EmailService;
import com.onlineshop.storage.api.model.SaleModel;
import com.onlineshop.user.api.model.UserModel;
import com.onlineshop.user.api.operations.cartitem.sellcart.SellCartResult;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class KafkaListeners {

    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    @KafkaListener(topics = "sendEmail", groupId = "email")
    public void consume(String data) throws JsonProcessingException {

        SellCartResult saleCart = objectMapper.readValue(data, SellCartResult.class);

        UserModel user = saleCart.getUser();
        List<SaleModel> sales = saleCart.getSales();

        String userJson = objectMapper.writeValueAsString(user);
        String salesJson = sales
                .stream()
                .map(s -> {
                    try {
                        return objectMapper.writeValueAsString(s);
                    } catch (JsonProcessingException e) {
                        throw new InvalidDataException();
                    }
                })
                .collect(Collectors.joining(" "));

        String text = "User: " + userJson + ", Sales: " + salesJson;

        emailService.sendEmail(user.getEmail(), text);
    }
}
