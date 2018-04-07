package com.example.redent0r.ethernal;

/**
 * @author redent0r
 *
 */

public class Lottery {
    private String id;
    private Double entryAmount;
    private String ownerId;
    private Integer maxParticipants;
    private Boolean completed;
    private Long time;

    public Lottery(String id, Double entryAmount, String ownerId, Integer maxParticipants, Boolean completed, Long time) {
        this.id = id;
        this.entryAmount = entryAmount;
        this.ownerId = ownerId;
        this.maxParticipants = maxParticipants;
        this.completed = completed;
        this.time = time;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public String getId() {
        return id;
    }

    public Double getEntryAmount() {
        return entryAmount;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public Long getTime() {
        return time;
    }

    public Boolean getCompleted() {
        return completed;
    }
}
