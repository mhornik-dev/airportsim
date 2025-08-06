package com.airportsim.controller;

import com.airportsim.factories.GroupGenerator;
import com.airportsim.manager.CheckinManager;
import com.airportsim.manager.EntryManager;
import com.airportsim.manager.EventLogger;
import com.airportsim.manager.SecurityManager;
import com.airportsim.manager.TerminalManager;
import com.airportsim.manager.TimeManager;
import com.airportsim.model.entities.*;
import com.airportsim.model.enums.EntryNames;
import com.airportsim.model.enums.TerminalNames;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SimulationController {

    private static final SimulationController INSTANCE = new SimulationController();

    private List<EntryArea> entryAreas;
    private List<CheckinStation> checkinStations;
    private List<SecurityStation> securityStations;
    private List<TerminalArea> terminalAreas;

    private List<EntryController> entryControllers;
    private EntryManager entryManager;

    private List<CheckinController> checkinControllers;
    private CheckinManager checkinManager;

    private List<SecurityController> securityControllers;
    private SecurityManager securityManager;

    private List<TerminalController> terminalControllers;
    private TerminalManager terminalManager;

    private TimeManager timeManager;
    private EventLogger eventLogger;

    private final ScheduledExecutorService groupGeneratorExecutor;
    private GroupGenerator groupGenerator;

    private SimulationController() {
        this.groupGeneratorExecutor = Executors.newSingleThreadScheduledExecutor();
        this.terminalAreas = new ArrayList<>();
        this.entryAreas = new ArrayList<>();
        this.checkinStations = new ArrayList<>();
        this.securityStations = new ArrayList<>();
        this.entryControllers = new ArrayList<>();
        this.checkinControllers = new ArrayList<>();
        this.securityControllers = new ArrayList<>();
        this.terminalControllers = new ArrayList<>();

        initializeGroupGenerator();
        initializeStations();
        initializeControllers();
        initializeManagers();
        initializeForwarder();
        initializeTimeManager();
        initializeEventLogger();
    }

    public static SimulationController getInstance() {
        return INSTANCE;
    }

    public void setStartTime(int startTimeInSeconds) {
        this.timeManager.setTime(startTimeInSeconds);
    }

    public void setSimulationSpeed(int simulationSpeed) {
        this.timeManager.setTimeIntervall(simulationSpeed);
    }

    private void initializeStations() {
        // Erstelle Entry-Station
        entryAreas.add(new EntryArea(1L, EntryNames.ENTRY_1A));
        // Erstelle Check-In-Stationen
        checkinStations.add(new CheckinStation(1L));
        checkinStations.add(new CheckinStation(2L));
        // Erstelle Sicherheits-Stationen
        securityStations.add(new SecurityStation(1L));
        securityStations.add(new SecurityStation(2L));
        securityStations.add(new SecurityStation(3L));
        // Erstelle Terminal-Bereiche
        terminalAreas.add(new TerminalArea(1L, TerminalNames.TERMINAL_1));
    }
    
    private void initializeManagers() {
        this.entryManager = new EntryManager(entryControllers);
        this.securityManager = new SecurityManager(securityControllers);
        this.checkinManager = new CheckinManager(checkinControllers);
        this.terminalManager = new TerminalManager(terminalControllers);
    }

    private void initializeForwarder() {
        // Setze Forwarder für EntryManager
        this.groupGenerator.setForwarder(entryManager);
        // Setze Forwarder für SecurityManager
        for (CheckinController checkinController : this.checkinControllers) {
            checkinController.setForwarder(securityManager);
        }
        // Setze Forwarder für CheckinManager
        for (EntryController entryController : this.entryControllers) {
            entryController.setForwarder(checkinManager);
        }
        // Setze Forwarder für TerminalManager
        for (SecurityController securityController : this.securityControllers) {
            securityController.setForwarder(terminalManager);
        }
    }

    private void initializeEventLogger() {
        this.eventLogger = new EventLogger();

        entryManager.setEventLogger(eventLogger);
        checkinManager.setEventLogger(eventLogger);
        securityManager.setEventLogger(eventLogger);
        terminalManager.setEventLogger(eventLogger);
        entryControllers.forEach(controller -> controller.setPassengerEventLogger(eventLogger));
        checkinControllers.forEach(controller -> controller.setPassengerEventLogger(eventLogger));
        securityControllers.forEach(controller -> controller.setPassengerEventLogger(eventLogger));
        terminalControllers.forEach(controller -> controller.setPassengerEventLogger(eventLogger));
    }

    private void initializeControllers() {
        for (EntryArea entry : entryAreas) {
            EntryController controller = new EntryController(entry);
            this.entryControllers.add(controller);
        }

        for (CheckinStation checkin : checkinStations) {
            CheckinController controller = new CheckinController(checkin);
            this.checkinControllers.add(controller);
        }

        for (SecurityStation security : securityStations) {
            SecurityController controller = new SecurityController(security);
            this.securityControllers.add(controller);
        }

        for (TerminalArea terminal : terminalAreas) {
            TerminalController controller = new TerminalController(terminal);
            this.terminalControllers.add(controller);
        }
    }

    private void initializeTimeManager() {
        this.timeManager = TimeManager.getInstance();
        
        entryManager.setTimeManager(timeManager);
        checkinManager.setTimeManager(timeManager);
        securityManager.setTimeManager(timeManager);
        terminalManager.setTimeManager(timeManager);
        entryControllers.forEach(controller -> controller.setTimeManager(timeManager));
        checkinControllers.forEach(controller -> controller.setTimeManager(timeManager));
        securityControllers.forEach(controller -> controller.setTimeManager(timeManager));
        terminalControllers.forEach(controller -> controller.setTimeManager(timeManager));
    }

    public void initializeGroupGenerator() {
        this.groupGenerator = new GroupGenerator();
    }

    public void startSimulation() {
        System.out.println("Starting airport simulation...");

        // Starte Zeitmanager
        timeManager.start();

        // Öffne alle Stationen
        entryControllers.forEach(EntryController::openEntry);
        checkinControllers.forEach(CheckinController::openCheckin);
        securityControllers.forEach(SecurityController::openSecurity);
        terminalControllers.forEach(TerminalController::openTerminal);

        
        try {
            // Starte Verarbeitung
            Thread.sleep(1000); // Kurze Pause zwischen den Starts
            entryManager.startProcessing();
            Thread.sleep(1000);
            checkinManager.startProcessing();
            Thread.sleep(1000);
            securityManager.startProcessing();
            Thread.sleep(1000);
            terminalManager.startProcessing();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Simulation start interrupted.");
        }

        // Starte Gruppen-Generator
        groupGeneratorExecutor.submit(groupGenerator::startGenerating);       
    }

    public void stopSimulation() {
        System.out.println("Stopping airport simulation...");

        entryControllers.forEach(EntryController::closeEntry);
        checkinControllers.forEach(CheckinController::closeCheckin);
        securityControllers.forEach(SecurityController::closeSecurity);
        terminalControllers.forEach(TerminalController::closeTerminal);

        if (timeManager != null) timeManager.shutdown();
        if (entryManager != null) entryManager.shutdown();
        if (checkinManager != null) checkinManager.shutdown();
        if (securityManager != null) securityManager.shutdown();
        if (terminalManager != null) terminalManager.shutdown();
        if (groupGenerator != null) groupGenerator.stop();
        groupGeneratorExecutor.shutdownNow();
        try {
            if (!groupGeneratorExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                System.err.println("GroupGenerator executor did not terminate in time.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }



}
