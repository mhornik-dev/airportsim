package com.airportsim.controller;

import com.airportsim.config.SimulationSettings;
import com.airportsim.manager.GroupForwarderInterface;
import com.airportsim.manager.GroupProviderInterface;
import com.airportsim.manager.EventLoggerInterface;
import com.airportsim.model.entities.CheckinStation;
import com.airportsim.model.entities.Group;
import com.airportsim.model.entities.Passenger;
import com.airportsim.model.entities.Station;
import com.airportsim.model.events.LogEvent.EventType;
import com.airportsim.util.OutputUtils;
import com.airportsim.manager.StationInterface;
import com.airportsim.manager.TimeManager;


public class CheckinController implements StationInterface {
    private CheckinStation checkin;
    private GroupProviderInterface groupProvider;
    private GroupForwarderInterface forwarder;
    private EventLoggerInterface passengerEventLogger;
    private TimeManager timeManager;
    private volatile boolean operating = false;

    public CheckinController(CheckinStation checkin) {
        this.checkin = checkin;
    }

    public void setProvider(GroupProviderInterface groupProvider) {
        this.groupProvider = groupProvider;
    }

    public void setForwarder(GroupForwarderInterface forwarder) {
        this.forwarder = forwarder;
    }

    public void setTimeManager(TimeManager timeManager) {
        this.timeManager = timeManager;
    }

    public void setPassengerEventLogger(EventLoggerInterface passengerEventLogger) {
        this.passengerEventLogger = passengerEventLogger;
    }

    public void openCheckin() {
        checkin.setStatus(Station.StationStatus.OPEN);
        System.out.println(timeManager.getCurrentTimeString() + " - Check-In Counter " + checkin.getId() + " is ready to process groups.");
    }

    public boolean isOpenCheckin() {
        return checkin.getStatus() == Station.StationStatus.OPEN;
    }

    public void closeCheckin() {
        stopProcessing();
        checkin.setStatus(Station.StationStatus.CLOSED);
        System.out.println(timeManager.getCurrentTimeString() + " - Check-In Counter " + checkin.getId() + " is now closed.");
    }

    public void startProcessing() {
        operating = true;

        try {
            while (operating) {
                if (isOpenCheckin()) {
                    Group<Passenger> group = groupProvider.getNextGroup();
                    groupProvider.onGroupTaken();
                    processGroup(group);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("CheckinController interrupted: " + e.getMessage());
        }
    }

    public void stopProcessing() {
        operating = false;
    }

    public void processGroup(Group<Passenger> group) {
        checkin.setCurrentGroup(group);
        checkin.setStatus(Station.StationStatus.PROCESSING);
        passengerEventLogger.logGroupEvent(group, EventType.START_PROCESSING_GROUP, timeManager.getCurrentSimulationTime(), "Check-In Counter " + checkin.getId());
        OutputUtils.printLastGroupEvent(passengerEventLogger, group);

        for (Passenger person : group.getMembers()) {
            checkin.setCurrentPerson(person);
            passengerEventLogger.logEvent(person, EventType.CHECK_IN_STARTED, timeManager.getCurrentSimulationTime(), "Check-In Counter " + checkin.getId());
            OutputUtils.printLastPassengerEvent(passengerEventLogger, person);
            simulateTimeDelay(SimulationSettings.checkin_passenger_processing_time); // Simulate processing time
            passengerEventLogger.logEvent(person, EventType.CHECK_IN_COMPLETED, timeManager.getCurrentSimulationTime(), "Check-In Counter " + checkin.getId());
            OutputUtils.printLastPassengerEvent(passengerEventLogger, person);
        }

        checkin.setCurrentPerson(null);
        checkin.setCurrentGroup(null);
        checkin.setStatus(Station.StationStatus.OPEN);
        passengerEventLogger.logGroupEvent(group, EventType.FINISH_PROCESSING_GROUP, timeManager.getCurrentSimulationTime(), "Check-In Counter " + checkin.getId());
        OutputUtils.printLastGroupEvent(passengerEventLogger, group);
        forwarder.forwardGroup(group);
    }

    public CheckinStation getCheckin() {
        return checkin;
    }

    private void simulateTimeDelay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("CheckinController: Time delay interrupted.");
        }
    }
}
