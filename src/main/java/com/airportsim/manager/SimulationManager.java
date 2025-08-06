package com.airportsim.manager;

import com.airportsim.config.SimulationSettings;
import com.airportsim.controller.SimulationController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationManager {
    
    private static final SimulationManager INSTANCE = new SimulationManager();
    private SimulationController simulationController;
    private int simulationSpeed = SimulationSettings.simulation_speed;
    private int startTime = SimulationSettings.simulation_start_time;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private SimulationManager() {}

    public static SimulationManager getInstance() {
        return INSTANCE;
    }

    private void initializeSimulation() {
        simulationController = SimulationController.getInstance();
        simulationController.setSimulationSpeed(this.simulationSpeed);
        simulationController.setStartTime(this.startTime);
    }


    public void startSimulation() {
        initializeSimulation();
        executor.submit(simulationController::startSimulation);
    }

    public void stopSimulation() {

        if (simulationController != null) { simulationController.stopSimulation(); }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                System.err.println("SimulationManager executor did not terminate in time.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
