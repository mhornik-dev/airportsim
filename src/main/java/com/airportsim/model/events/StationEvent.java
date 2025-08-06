package com.airportsim.model.events;

import com.airportsim.model.entities.SimulationTime;
import com.airportsim.model.entities.Station;
import com.airportsim.model.entities.Group;
import com.airportsim.model.entities.Passenger;

public class StationEvent extends LogEvent<Group<Passenger>> {

    private final Long stationId;

    public StationEvent(Station station, EventType type, SimulationTime time, String note) {
        super(type, time, note);
        this.stationId = station.getId();
    }

    public Long getStationId() {
        return stationId;
    }

    @Override
    public String toString() {
        return "StationID: " + getStationId() + " - " + super.toString();
    }

}
