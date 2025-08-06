package com.airportsim;
import com.airportsim.manager.SimulationManager;


public class Main {
    public static void main(String[] args) {

        SimulationManager test =  SimulationManager.getInstance();
        test.startSimulation();

    }

}
