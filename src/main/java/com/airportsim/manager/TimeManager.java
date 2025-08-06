package com.airportsim.manager;

import java.time.LocalTime;
import java.util.concurrent.*;

import com.airportsim.config.SimulationSettings;
import com.airportsim.model.entities.SimulationTime;

public class TimeManager {

    private static final TimeManager INSTANCE = new TimeManager();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private int simulatedSeconds; // Sekunden seit Mitternacht
    private int timeIntervall; // Millisekunden für das Intervall ()
    private volatile boolean running = false;

    private TimeManager() {
        this.simulatedSeconds = 0;
        this.timeIntervall = 1000; 
    }

    public static TimeManager getInstance() {
        return INSTANCE;
    }

    public void setTime(int simulatedSeconds) { this.simulatedSeconds = simulatedSeconds; }

    public void setTimeIntervall(int timeIntervall) { this.timeIntervall = timeIntervall; }

    public void start() {
        if (running) return;
        running = true;

        scheduler.scheduleAtFixedRate(() -> {
            if (!running) return;

            simulatedSeconds = (simulatedSeconds + 1) % (24 * 60 * 60);

        }, 0, timeIntervall, TimeUnit.MILLISECONDS);
    }

    public void pause() {
        running = false;
        scheduler.shutdownNow();
        System.out.println("Time has paused at: " + getCurrentTimeString());
    }

    public void shutdown() {
        running = false;
        scheduler.shutdownNow();
        simulatedSeconds = SimulationSettings.simulation_start_time;
        System.out.println("TimeManager shutdown: Simulation time reset to start time.");
    }

    public SimulationTime getCurrentSimulationTime() {
        return new SimulationTime(simulatedSeconds);
    }

    public LocalTime getCurrentSimulationTimeAsLocalTime() {
        return getCurrentSimulationTime().toLocalTime();
    }

    public String getCurrentTimeString() {
        return getCurrentSimulationTime().toString();
    }
}
