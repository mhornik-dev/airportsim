package com.airportsim.model.events;

import com.airportsim.model.entities.SimulationTime;
import com.airportsim.model.entities.Passenger;

public class PassengerEvent extends LogEvent<Passenger> {

    private final Long personId;

    public PassengerEvent(Passenger person, EventType type, SimulationTime time, String note) {
        super(type, time, note);
        this.personId = person.getId();
    }

    public Long getPersonId() {
        return personId;
    }

    @Override
    public String toString() {
        return "PersonID: " + getPersonId() + " - " + super.toString();
    }
}

