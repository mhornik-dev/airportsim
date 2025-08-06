package com.airportsim.manager;

import com.airportsim.model.entities.Group;
import com.airportsim.model.entities.Passenger;
import com.airportsim.model.events.LogEvent.EventType;
import com.airportsim.util.OutputUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public abstract class AbstractAreaManager<T extends AreaInterface> implements GroupProviderInterface, GroupForwarderInterface {

    private final BlockingQueue<Group<Passenger>> processingQueue;
    private final List<T> controllers;
    private final ExecutorService executorService;
    private final String managerName;
    private EventLoggerInterface eventLogger;
    private TimeManager timeManager;

    public AbstractAreaManager(List<T> controllers, String managerName) {
        this.processingQueue = new LinkedBlockingQueue<>();
        this.controllers = new ArrayList<>(controllers);
        this.executorService = Executors.newFixedThreadPool(controllers.size());
        this.managerName = managerName;

        for (T controller : this.controllers) {
            controller.setProvider(this);
        }

        
    }

    public void setEventLogger(EventLoggerInterface eventLogger) {
        this.eventLogger = eventLogger;
    }

    public void setTimeManager(TimeManager timeManager) {
        this.timeManager = timeManager;
    }

    public String getManagerName() {
        return managerName;
    }

    public void addGroupToQueue(Group<Passenger> group) {
        try {
            processingQueue.put(group);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            eventLogger.logAreaManagerEvent(this, EventType.ERROR, timeManager.getCurrentSimulationTime(), e.getMessage());
            OutputUtils.printLastManagerEvent(eventLogger, this);
        }
    }

    @Override
    public Group<Passenger> getNextGroup() throws InterruptedException {
        return processingQueue.take();
    }

    @Override
    public void onGroupTaken() {
        // In einem Area-Kontext optional, z. B. für visuelle Anzeige
    }

    @Override
    public void forwardGroup(Group<Passenger> group) {
        addGroupToQueue(group);
    }

    public void startProcessing() {
        for (T controller : controllers) {
            executorService.submit(controller::startForwarding);
        }
    }

    public void shutdown() {
        for (T controller : controllers) {
            controller.stopForwarding();
        }

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                System.out.println(managerName + " did not terminate in time. Forcing shutdown.");
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

