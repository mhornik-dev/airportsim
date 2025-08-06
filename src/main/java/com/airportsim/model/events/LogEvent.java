package com.airportsim.model.events;

import com.airportsim.model.entities.SimulationTime;

public class LogEvent<T> {

    private final EventType type;
    private final SimulationTime time;
    private final String note;

    public LogEvent(EventType type, SimulationTime time, String note) {
        this.type = type;
        this.time = time;
        this.note = note;
    }

    public EventType getType() {
        return type;
    }

    public SimulationTime getTime() {
        return time;
    }

    public String getNote() {
        return note;
    }

    @Override
    public String toString() {
        return "Eventtype: " + type + " - Timestamp: " + (time != null ? time : "N/A") +
            (note != null && !note.isEmpty() ? " - Location: " + note : "");
    }

    public enum EventType {
        ERROR,
        START_PROCESSING_GROUP,
        FINISH_PROCESSING_GROUP,
        MOVING_TO_CHECKIN_QUEUE,
        WAITING_IN_QUEUE,
        READY_FOR_PROCESSING,
        CHECK_IN_STARTED,
        CHECK_IN_REJECTED,
        CHECK_IN_COMPLETED,
        SECURITY_CHECK_STARTED,
        SECURITY_CHECK_REJECTED,
        SECURITY_CHECK_COMPLETED,
        ARRIVED_AT_AREA,
        ARRIVED_AT_TERMINAL,
        ARRIVED_AT_ENTRY,
        ARRIVED_AT_GATE,
        MOVING_TO_BOARDING_QUEUE,
        BOARDING_STARTED,
        BOARDING_REJECTED,
        BOARDING_COMPLETED,
    }

}

