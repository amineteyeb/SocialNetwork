package com.esprit.socialnetwork.Transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
// PaymeePaymentRequest.java
// Include necessary annotations, getters, setters, etc.
public class PaymeePaymentRequest {
    private double amount;
    private String note;
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private String return_url;
    private String cancel_url;
    private String webhook_url;
    private String orderId;
}

