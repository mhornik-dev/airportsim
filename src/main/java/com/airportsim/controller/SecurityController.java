package com.airportsim.controller;

import com.airportsim.config.SimulationSettings;
import com.airportsim.manager.GroupProviderInterface;
import com.airportsim.manager.GroupForwarderInterface;
import com.airportsim.manager.EventLoggerInterface;
import com.airportsim.manager.StationInterface;
import com.airportsim.model.entities.Group;
import com.airportsim.model.entities.Passenger;
import com.airportsim.model.entities.SecurityStation;
import com.airportsim.model.entities.Station;
import com.airportsim.model.events.LogEvent.EventType;
import com.airportsim.util.OutputUtils;
import com.airportsim.manager.TimeManager;

public class SecurityController implements StationInterface {
    private SecurityStation security;
    private GroupProviderInterface groupProvider;
    private GroupForwarderInterface forwarder;
    private EventLoggerInterface passengerEventLogger;
    private TimeManager timeManager;
    private volatile boolean operating = false;

    public SecurityController(SecurityStation security) {
        this.security = security;
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

    public void openSecurity() {
        security.setStatus(Station.StationStatus.OPEN);
        System.out.println(timeManager.getCurrentTimeString() + " - Security Station " + security.getId() + " is ready to process groups.");
    }

    public boolean isOpenSecurity() {
        return security.getStatus() == Station.StationStatus.OPEN;
    }

    public void closeSecurity() {
        stopProcessing();
        security.setStatus(Station.StationStatus.CLOSED);
        System.out.println(timeManager.getCurrentTimeString() + " - Security Station " + security.getId() + " is now closed.");
    }

    public void startProcessing() {
        operating = true;

        try {
            while (operating) {
                if (isOpenSecurity()) {
                    Group<Passenger> group = groupProvider.getNextGroup();
                    groupProvider.onGroupTaken();
                    processGroup(group);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("SecurityController interrupted: " + e.getMessage());
        }
    }

    public void stopProcessing() {
        operating = false;
    }

    public void processGroup(Group<Passenger> group) {
        security.setCurrentGroup(group);
        security.setStatus(Station.StationStatus.PROCESSING);
        passengerEventLogger.logGroupEvent(group, EventType.START_PROCESSING_GROUP, timeManager.getCurrentSimulationTime(), "Security Station " + security.getId());
        OutputUtils.printLastGroupEvent(passengerEventLogger, group);

        for (Passenger person : group.getMembers()) {
            security.setCurrentPerson(person);
            passengerEventLogger.logEvent(person, EventType.SECURITY_CHECK_STARTED, timeManager.getCurrentSimulationTime(), "Security Station " + security.getId());
            OutputUtils.printLastPassengerEvent(passengerEventLogger, person);
            simulateTimeDelay(SimulationSettings.security_passenger_processing_time); // Simulate processing time
            passengerEventLogger.logEvent(person, EventType.SECURITY_CHECK_COMPLETED, timeManager.getCurrentSimulationTime(), "Security Station " + security.getId());
            OutputUtils.printLastPassengerEvent(passengerEventLogger, person);
        }

        security.setCurrentPerson(null);
        security.setCurrentGroup(null);
        security.setStatus(Station.StationStatus.OPEN);
        passengerEventLogger.logGroupEvent(group, EventType.FINISH_PROCESSING_GROUP, timeManager.getCurrentSimulationTime(), "Security Station " + security.getId());
        OutputUtils.printLastGroupEvent(passengerEventLogger, group);
        forwarder.forwardGroup(group);
    }

    public SecurityStation getSecurity() {
        return security;
    }

    private void simulateTimeDelay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("SecurityController: Time delay interrupted.");
        }
    }

}

