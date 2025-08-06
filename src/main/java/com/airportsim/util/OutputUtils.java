package com.airportsim.util;

import com.airportsim.model.entities.Passenger;
import com.airportsim.model.entities.Group;
import com.airportsim.model.entities.Station;
import com.airportsim.manager.AbstractAreaManager;
import com.airportsim.manager.AbstractStationManager;
import com.airportsim.model.events.PassengerEvent;
import com.airportsim.model.events.GroupEvent;
import com.airportsim.model.events.StationEvent;
import com.airportsim.model.events.StationManagerEvent;
import com.airportsim.model.events.AreaManagerEvent;
import com.airportsim.manager.EventLoggerInterface;

public class OutputUtils {

    private OutputUtils() {}

    public static void printLastPassengerEvent(EventLoggerInterface logger, Passenger person) {
        PassengerEvent lastEvent = logger.getLastEvent(person);
        if (lastEvent != null) {
            System.out.println(lastEvent);
        } else {
            System.out.println("Person " + person.getId() + " has no events logged");
        }
    }

    public static void printLastGroupEvent(EventLoggerInterface logger, Group<Passenger> group) {
        GroupEvent lastEvent = logger.getLastEvent(group);
        if (lastEvent != null) {
            System.out.println(lastEvent);
        } else {
            System.out.println("Group " + group.getId() + " has no events logged");
        }
    }

    public static void printLastEventForEachPassenger(EventLoggerInterface logger, Group<Passenger> group) {
        for (Passenger person : group.getMembers()) {
            PassengerEvent lastEvent = logger.getLastEvent(person);
            if (lastEvent != null) {
                System.out.println(lastEvent);
            } else {
                System.out.println("Person " + person.getId() + " has no events logged");
            }
        }
    }

    public static void printLastManagerEvent(EventLoggerInterface logger, AbstractStationManager<?> manager) {
        StationManagerEvent lastEvent = logger.getLastEvent(manager);
        if (lastEvent != null) {
            System.out.println(lastEvent);
        } else {
            System.out.println("Manager " + manager.getManagerName() + " has no events logged");
        }
    }

    public static void printLastManagerEvent(EventLoggerInterface logger, AbstractAreaManager<?> manager) {
        AreaManagerEvent lastEvent = logger.getLastEvent(manager);
        if (lastEvent != null) {
            System.out.println(lastEvent);
        } else {
            System.out.println("Area Manager " + manager.getManagerName() + " has no events logged");
        }
    }

    public static void printLastStationEvent(EventLoggerInterface logger, Station station) {
        StationEvent lastEvent = logger.getLastEvent(station);
        if (lastEvent != null) {
            System.out.println(lastEvent);
        } else {
            System.out.println("Station " + station.getId() + " has no events logged");
        }
    }

}
