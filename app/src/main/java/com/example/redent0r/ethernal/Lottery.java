package com.example.redent0r.ethernal;

/**
 * @author redent0r
 *
 */

public class Lottery {
    String id;
    Double entryAmount;
    String winnerId;
    Boolean completed;
    Long time;

    public Lottery(String id, Double entryAmount, String winnerId, Boolean completed, Long time) {
        this.id = id;
        this.entryAmount = entryAmount;
        this.winnerId = winnerId;
        this.completed = completed;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public Double getEntryAmount() {
        return entryAmount;
    }

    public String getWinnerId() {
        return winnerId;
    }

    public Long getTime() {
        return time;
    }

    public Boolean getCompleted() {
        return completed;
    }
}
