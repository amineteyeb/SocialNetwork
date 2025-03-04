package com.esprit.socialnetwork.Transaction;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class PaymeePaymentResponse implements Serializable {
    private boolean status;
    private String message;
    private int code;
    private PaymeePaymentData data;

    public PaymeePaymentResponse(boolean paymentStatus) {
        this.status = paymentStatus;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(PaymeePaymentData data) {
        this.data = data;
    }
// getters and setters
}