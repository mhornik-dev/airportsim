package com.airportsim.manager;

import com.airportsim.model.entities.Passenger;
import com.airportsim.model.entities.Group;
import com.airportsim.model.entities.Station;
import com.airportsim.model.events.PassengerEvent;
import com.airportsim.model.events.GroupEvent;
import com.airportsim.model.events.StationEvent;
import com.airportsim.model.events.AreaManagerEvent;
import com.airportsim.model.events.LogEvent.EventType;
import com.airportsim.model.events.StationManagerEvent;
import com.airportsim.model.entities.SimulationTime;


public interface EventLoggerInterface {

    void logEvent(Passenger person, EventType type, SimulationTime time, String note);
    void logEvent(Passenger person, EventType type, SimulationTime time);
    void logEvent(Group<Passenger> group, EventType type, SimulationTime time, String note);
    void logEvent(Group<Passenger> group, EventType type, SimulationTime time);
    void logGroupEvent(Group<Passenger> group, EventType type, SimulationTime time, String note);
    void logGroupEvent(Group<Passenger> group, EventType type, SimulationTime time);
    void logStationManagerEvent(AbstractStationManager<?> manager, EventType type, SimulationTime time, String note);
    void logAreaManagerEvent(AbstractAreaManager<?> manager, EventType type, SimulationTime time, String note);
    PassengerEvent getLastEvent(Passenger person);
    GroupEvent getLastEvent(Group<Passenger> group);
    StationEvent getLastEvent(Station station);
    StationManagerEvent getLastEvent(AbstractStationManager<?> manager);
    AreaManagerEvent getLastEvent(AbstractAreaManager<?> manager);
}

