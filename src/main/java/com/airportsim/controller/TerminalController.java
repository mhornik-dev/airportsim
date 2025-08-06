package com.airportsim.controller;

import com.airportsim.manager.GroupProviderInterface;
import com.airportsim.manager.EventLoggerInterface;
import com.airportsim.manager.AreaInterface;
import com.airportsim.manager.TimeManager;
import com.airportsim.model.entities.Area.AreaStatus;
import com.airportsim.model.entities.Group;
import com.airportsim.model.entities.Passenger;
import com.airportsim.model.entities.TerminalArea;
import com.airportsim.model.events.LogEvent.EventType;
import com.airportsim.util.OutputUtils;

public class TerminalController implements AreaInterface {

    private TerminalArea terminal;
    private GroupProviderInterface groupProvider;
    private EventLoggerInterface passengerEventLogger;
    private TimeManager timeManager;
    private volatile boolean operating = false;

    public TerminalController(TerminalArea terminal) {
        this.terminal = terminal;
    }

    public void setProvider(GroupProviderInterface groupProvider) {
        this.groupProvider = groupProvider;
    }

    public void setPassengerEventLogger(EventLoggerInterface passengerEventLogger) {
        this.passengerEventLogger = passengerEventLogger;
    }

    public void setTimeManager(TimeManager timeManager) {
        this.timeManager = timeManager;
    }

    public TerminalArea getTerminal() {
        return terminal;
    }

    public void openTerminal() {
        terminal.setStatus(AreaStatus.OPEN);
        System.out.println(timeManager.getCurrentTimeString() + " - Terminal " + terminal.getId() + " is now open for groups.");
    }

    public boolean isOpenTerminal() {
        return terminal.getStatus() == AreaStatus.OPEN;
    }

    public void closeTerminal() {
        stopForwarding();
        terminal.setStatus(AreaStatus.CLOSED);
        System.out.println(timeManager.getCurrentTimeString() + " - Terminal " + terminal.getId() + " is now closed.");
    }

    public void startForwarding() {
        operating = true;

        try {
            while (operating) {
                if (isOpenTerminal()) {
                    Group<Passenger> group = groupProvider.getNextGroup();
                    terminal.addGroup(group);

                    // Terminal ist Endstation – kein forwarder mehr notwendig
                    passengerEventLogger.logGroupEvent(group, EventType.ARRIVED_AT_TERMINAL, timeManager.getCurrentSimulationTime(), terminal.getName().getName());
                    OutputUtils.printLastGroupEvent(passengerEventLogger, group);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("TerminalController: Interrupted.");
        }
    }

    public void stopForwarding() {
        operating = false;
    }
}

