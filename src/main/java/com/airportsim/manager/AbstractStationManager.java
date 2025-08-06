package com.airportsim.manager;

import com.airportsim.model.entities.Group;
import com.airportsim.model.entities.Passenger;
import com.airportsim.model.events.LogEvent.EventType;
import com.airportsim.util.OutputUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


// T muss GroupController sein (damit setProvider() und startProcessing() aufgerufen werden kann)
public abstract class AbstractStationManager<T extends StationInterface> implements GroupProviderInterface, GroupForwarderInterface {

    private final BlockingQueue<Group<Passenger>> mainQueue;
    private final BlockingQueue<Group<Passenger>> processingQueue;
    private final List<T> controllers;
    private final ExecutorService executorService;
    private final String managerName;
    private EventLoggerInterface eventLogger;
    private TimeManager timeManager;

    public AbstractStationManager(List<T> controllers, String managerName) {
        this.mainQueue = new LinkedBlockingQueue<>();
        this.processingQueue = new LinkedBlockingQueue<>(controllers.size());
        this.controllers = new ArrayList<>(controllers);
        this.executorService = Executors.newFixedThreadPool(controllers.size());
        this.managerName = managerName;

        for (T controller : this.controllers) {
            controller.setProvider(this);
        }
    }

    public String getManagerName() {
        return managerName;
    }

    public void setEventLogger(EventLoggerInterface eventLogger) {
        this.eventLogger = eventLogger;
    }

    public void setTimeManager(TimeManager timeManager) {
        this.timeManager = timeManager;
    }

    public void addGroupToQueue(Group<Passenger> group) {
        try {
            mainQueue.put(group);
            eventLogger.logGroupEvent(group, EventType.WAITING_IN_QUEUE, timeManager.getCurrentSimulationTime());
            OutputUtils.printLastGroupEvent(eventLogger, group);
            addGroupToProcessing();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            eventLogger.logStationManagerEvent(this, EventType.ERROR, timeManager.getCurrentSimulationTime(), e.getMessage());
            OutputUtils.printLastManagerEvent(eventLogger, this);
        }
    }

    private synchronized void addGroupToProcessing() {
        while (processingQueue.remainingCapacity() > 0 && !mainQueue.isEmpty()) {
            try {
                Group<Passenger> group = mainQueue.take();
                processingQueue.put(group);
                eventLogger.logGroupEvent(group, EventType.READY_FOR_PROCESSING, timeManager.getCurrentSimulationTime());
                OutputUtils.printLastGroupEvent(eventLogger, group);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public Group<Passenger> getNextGroup() throws InterruptedException {
        return processingQueue.take();
    }

    @Override
    public void onGroupTaken() {
        addGroupToProcessing();
    }

    @Override
    public void forwardGroup(Group<Passenger> group) {
        addGroupToQueue(group);
    }

    public void startProcessing() {
        for (T controller : controllers) {
            executorService.submit(controller::startProcessing);
        }
    }

    public void shutdown() {
        for (T controller : controllers) {
            controller.stopProcessing();
        }
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                System.out.println(managerName + " did not terminate in the specified time.");
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
        
        System.out.println(managerName + ": Shutdown complete.");
    }

    public List<T> getControllers() {
        return controllers;
    }
}

