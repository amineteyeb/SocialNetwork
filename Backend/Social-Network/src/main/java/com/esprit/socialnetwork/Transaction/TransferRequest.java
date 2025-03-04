package com.esprit.socialnetwork.Transaction;
public class TransferRequest {

    private Long senderId;
    private Long receiverId;
    private Double amount;
    private String externalId;
    private String note;

    // Constructors, getters, and setters

    public TransferRequest() {
    }

    public TransferRequest(Long senderId, Long receiverId, Double amount, String externalId, String note) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
        this.externalId = externalId;
        this.note = note;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

