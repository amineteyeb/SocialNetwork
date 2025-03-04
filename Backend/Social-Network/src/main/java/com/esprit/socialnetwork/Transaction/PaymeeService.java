package com.esprit.socialnetwork.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

// PaymeeService.java
@Service
public class PaymeeService implements  ITransactionService {

    @Value("${paymee.api.base-url}")
    private String apiUrl;

    @Value("${paymee.api.key}")
    private String apiKey;
@Autowired
    private  RestTemplate restTemplate;

    // Constructor, Autowire RestTemplate

    public String createPayment(PaymeePaymentRequest paymentRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "token " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PaymeePaymentRequest> requestEntity = new HttpEntity<>(paymentRequest, headers);
        System.out.println(Objects.requireNonNull(requestEntity.getBody()));
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl + "/api/v2/payments/create", requestEntity, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {

            System.out.println(Objects.requireNonNull(responseEntity.getBody()));
            return responseEntity.getBody();
        } else {
            // Handle error scenarios
            return null;
        }
    }

    // Add a method to check payment status if needed
}


    // Add a method to check payment status if needed

