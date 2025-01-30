package com.esprit.socialNetwork.Transaction;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class PaymeePaymentData implements Serializable {
    private String token;
    private String order_id;
    private String first_name;
    private String last_name;
    private String email;
    private double amount;

    public double getAmount() {
        return amount;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPayment_url(String payment_url) {
        this.payment_url = payment_url;
    }

    private String phone;
    private String note;

    private String payment_url;

    // getters and setters
}