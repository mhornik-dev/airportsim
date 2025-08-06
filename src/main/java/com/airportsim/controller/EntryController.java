package com.airportsim.controller;

import com.airportsim.manager.GroupForwarderInterface;
import com.airportsim.manager.GroupProviderInterface;
import com.airportsim.config.SimulationSettings;
import com.airportsim.manager.AreaInterface;
import com.airportsim.manager.EventLoggerInterface;
import com.airportsim.model.entities.EntryArea;
import com.airportsim.model.entities.Group;
import com.airportsim.model.entities.Passenger;
import com.airportsim.model.entities.Area.AreaStatus;

import com.airportsim.model.events.LogEvent.EventType;
import com.airportsim.util.OutputUtils;


import com.airportsim.manager.TimeManager;

public class EntryController implements AreaInterface {

    private EntryArea entry;
    private GroupProviderInterface groupProvider;
    private GroupForwarderInterface forwarder;
    private EventLoggerInterface passengerEventLogger;
    private TimeManager timeManager;
    private volatile boolean operating = false;

    public EntryController(EntryArea entry) {
        this.entry = entry;
    }

    public void setProvider(GroupProviderInterface groupProvider) {
        this.groupProvider = groupProvider;
    }

    public void setForwarder(GroupForwarderInterface forwarder) {
        this.forwarder = forwarder;
    }

    public void setPassengerEventLogger(EventLoggerInterface passengerEventLogger) {
        this.passengerEventLogger = passengerEventLogger;
    }

    public void setTimeManager(TimeManager timeManager) {
        this.timeManager = timeManager;
    }

    public EntryArea getEntry() {
        return entry;
    }

    public void openEntry() {
        entry.setStatus(AreaStatus.OPEN);
        System.out.println(timeManager.getCurrentTimeString() + " - Entry " + entry.getId() + " is now open for groups.");
    }

    public boolean isOpenEntry() {
        return entry.getStatus() == AreaStatus.OPEN;
    }

    public void closeEntry() {
        stopForwarding();
        entry.setStatus(AreaStatus.CLOSED);
        System.out.println(timeManager.getCurrentTimeString() + " - Entry " + entry.getId() + " is now closed.");
    }

    public void startForwarding() {
        operating = true;

        try {
            while (operating) {
                if (isOpenEntry()) {
                    Group<Passenger> group = groupProvider.getNextGroup();
                    passengerEventLogger.logGroupEvent(group, EventType.ARRIVED_AT_ENTRY, timeManager.getCurrentSimulationTime(), entry.getName().getName());
                    OutputUtils.printLastGroupEvent(passengerEventLogger, group);
                    simulateTimeDelay(SimulationSettings.entry_passenger_average_time); // Simulate average Time a Passenger-Group stays in Entry Area before moving to Check-in
                    passengerEventLogger.logGroupEvent(group, EventType.MOVING_TO_CHECKIN_QUEUE, timeManager.getCurrentSimulationTime(), entry.getName().getName());
                    OutputUtils.printLastGroupEvent(passengerEventLogger, group);
                    forwarder.forwardGroup(group);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("EntryController: Interrupted.");
        }
    }

    public void stopForwarding() {
        operating = false;
    }

    private void simulateTimeDelay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("EntryController: Time delay interrupted.");
        }
    }


}
