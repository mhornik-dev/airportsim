package com.airportsim.model.entities;

public abstract class Station {
    private Long id;
    private StationType type;
    private StationStatus status;

    public Station(Long id, StationType type) {
        this.id = id;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public StationType getType() {
        return type;
    }

    public StationStatus getStatus() {
        return status;
    }

    public void setStatus(StationStatus status) {
        this.status = status;
    }

    public enum StationType {
        ENTRY, CHECKIN, SECURITY, TERMINAL
    }

    public enum StationStatus {
        OPEN, PROCESSING, CLOSED
    }

}
