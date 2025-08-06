package com.airportsim.factories;

import com.airportsim.model.entities.*;
import com.airportsim.config.SimulationSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PassengerFactory {

    private static final Random random = new Random();
    private static long idCounter = 1;

    private static List<Luggage> generateRandomLuggage() {
        int count = random.nextInt(SimulationSettings.max_random_luggage + 1);
        List<Luggage> luggage = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            luggage.add(new Luggage());
        }
        return luggage;
    }

    public static Passenger createMan() {
        Passenger man = new Man(idCounter++);
        man.addLuggage(generateRandomLuggage());
        return man;
    }

    public static Passenger createWoman() {
        Passenger woman = new Woman(idCounter++);
        woman.addLuggage(generateRandomLuggage());
        return woman;
    }

    public static Passenger createChild() {
        Passenger child = new Child(idCounter++);
        child.addLuggage(generateRandomLuggage());
        return child;
    }
}

