package com.airportsim.model.entities;

public abstract class Area {
    private Long id;
    private AreaType type;
    private AreaStatus status;

    public Area(Long id, AreaType type) {
        this.id = id;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public AreaType getType() {
        return type;
    }

    public AreaStatus getStatus() {
        return status;
    }

    public void setStatus(AreaStatus status) {
        this.status = status;
    }

    public enum AreaType {
        ENTRY, TERMINAL, GATE
    }

    public enum AreaStatus {
        OPEN, ACTIVE, CLOSED
    }
}
