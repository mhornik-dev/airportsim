package com.airportsim.config;

public class SimulationSettings {

    public static int max_random_luggage = 2;

    public static int simulation_start_time = 60*60*8;

    // z.B. 100000 ms = 100 Sekunden Echtzeit entspricht 1 Sekunde Simulation
    // z.B. 10000 ms = 10 Sekunde Echtzeit entspricht 1 Sekunde Simulation
    // z.B. 1000 ms = 1 Sekunde Echtzeit entspricht 1 Sekunde Simulation
    // z.B. 100 ms = 1 Sekunde Echtzeit entspricht 10 Sekunden Simulation
    // z.B. 10 ms = 1 Sekunde Echtzeit entspricht 100 Sekunden Simulation
    public static int simulation_speed = 10;

    // GroupGenerator
    public static int max_generation_interval = simulation_speed * 600;
    public static int min_generation_interval = simulation_speed * 120;
    public static int max_group_count = 1; // Maximal 5 Personen pro Gruppe
    public static int min_group_count = 1; // Minimal 1 Person pro Gruppe
    public static int delay_between_groups = simulation_speed * 30; // 30 Sekunden zwischen Gruppen

    public static int entry_passenger_average_time = simulation_speed * 360; // 6 Minute Simulationszeit
    public static int checkin_passenger_processing_time = simulation_speed * 60; // 1 Minute Simulationszeit
    public static int checkin_luggage_processing_time = simulation_speed * 60; // 2 Minuten Simulationszeit
    public static int security_passenger_processing_time = simulation_speed * 120; // 2 Minuten Simulationszeit
    public static int boarding_passenger_processing_time = simulation_speed * 60; // 1 Minute Simulationszeit
}
