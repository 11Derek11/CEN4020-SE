package com.example.redent0r.ethernal;

/**
 * @author redent0r
 *
 */

public class Transaction {
    String id;
    Double amount;
    String winnerId;
    String looserId;
    Long time;

    public Transaction(String id, Double amount, String winnerId, String looserId, Long time) {
        this.id = id;
        this.amount = amount;
        this.winnerId = winnerId;
        this.looserId = looserId;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(String winnerId) {
        this.winnerId = winnerId;
    }

    public String getLooserId() {
        return looserId;
    }

    public void setLooserId(String looserId) {
        this.looserId = looserId;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
