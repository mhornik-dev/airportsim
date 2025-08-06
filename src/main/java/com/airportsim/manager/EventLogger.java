package com.airportsim.manager;

import com.airportsim.model.entities.*;
import com.airportsim.model.events.*;
import com.airportsim.model.events.LogEvent.EventType;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class EventLogger implements EventLoggerInterface {

    private final Map<String, List<StationManagerEvent>> managerLog = new HashMap<>();
    private final Map<Long, List<PassengerEvent>> personLog = new HashMap<>();
    private final Map<Long, List<GroupEvent>> groupLog = new HashMap<>();
    private final Map<Long, List<StationEvent>> stationLog = new HashMap<>();
    private final Map<String, List<AreaManagerEvent>> areaLog = new HashMap<>();

    @Override
    public void logEvent(Passenger person, EventType type, SimulationTime time, String note) {
        List<PassengerEvent> events = personLog.get(person.getId());
        if (events == null) {
            events = new ArrayList<>();
            personLog.put(person.getId(), events);
        }
        events.add(new PassengerEvent(person,type, time, note));
    }

    @Override
    public void logEvent(Passenger person, EventType type, SimulationTime time) {
        logEvent(person, type, time, null);
    }

    @Override
    public void logEvent(Group<Passenger> group, EventType type, SimulationTime time, String note) {
        for (Passenger person : group.getMembers()) {
            logEvent(person, type, time, note);
        }
    }

    @Override
    public void logEvent(Group<Passenger> group, EventType type, SimulationTime time) {
        for (Passenger person : group.getMembers()) {
            logEvent(person, type, time);
        }
    }

    @Override
    public void logGroupEvent(Group<Passenger> group, EventType type, SimulationTime time, String note) {
        List<GroupEvent> events = groupLog.get(group.getId());
        if (events == null) {
            events = new ArrayList<>();
            groupLog.put(group.getId(), events);
        }
        events.add(new GroupEvent(group, type, time, note));
    }

    @Override
    public void logGroupEvent(Group<Passenger> group, EventType type, SimulationTime time) {
        logGroupEvent(group, type, time, null);
    }

    @Override
    public void logStationManagerEvent(AbstractStationManager<?> manager, EventType type, SimulationTime time, String note) {
        List<StationManagerEvent> events = managerLog.get(manager.getManagerName());
        if (events == null) {
            events = new ArrayList<>();
            managerLog.put(manager.getManagerName(), events);
        }
        events.add(new StationManagerEvent(manager, type, time, note));
    }

    @Override
    public void logAreaManagerEvent(AbstractAreaManager<?> manager, EventType type, SimulationTime time, String note) {
        List<AreaManagerEvent> events = areaLog.get(manager.getManagerName());
        if (events == null) {
            events = new ArrayList<>();
            areaLog.put(manager.getManagerName(), events);
        }
        events.add(new AreaManagerEvent(manager, type, time, note));
    }

    @Override
    public PassengerEvent getLastEvent(Passenger person) {
        List<PassengerEvent> events = personLog.get(person.getId());
        if (events == null || events.isEmpty()) return null;
        return events.get(events.size() - 1);
    }

    @Override
    public GroupEvent getLastEvent(Group<Passenger> group) {
        List<GroupEvent> events = groupLog.get(group.getId());
        if (events == null || events.isEmpty()) return null;
        return events.get(events.size() - 1);
    }

    @Override
    public StationManagerEvent getLastEvent(AbstractStationManager<?> manager) {
        List<StationManagerEvent> events = managerLog.get(manager.getManagerName());
        if (events == null || events.isEmpty()) return null;
        return events.get(events.size() - 1);
    }

    @Override
    public AreaManagerEvent getLastEvent(AbstractAreaManager<?> manager) {
        List<AreaManagerEvent> events = areaLog.get(manager.getManagerName());
        if (events == null || events.isEmpty()) return null;
        return events.get(events.size() - 1);
    }

    @Override
    public StationEvent getLastEvent(Station station) {
        List<StationEvent> events = stationLog.get(station.getId());
        if (events == null || events.isEmpty()) return null;
        return events.get(events.size() - 1);
    }

}
