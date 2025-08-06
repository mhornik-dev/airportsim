package com.airportsim.factories;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.airportsim.model.entities.Passenger;
import com.airportsim.model.entities.Group;

public class GroupFactory {
    private static final Random random = new Random();
    private static long groupIdCounter = 1;

    public static Group<Passenger> createRandomGroup() {
        Group<Passenger> group = new Group<>(groupIdCounter++);
        List<Passenger> members = generateRandomMembers();

        for (Passenger m : members) {
            group.addMember(m);
        }

        return group;
    }

    private static List<Passenger> generateRandomMembers() {
        int type = random.nextInt(8);
        List<Passenger> members = new ArrayList<>();

        switch (type) {
            case 0 -> {
                members.add(PassengerFactory.createMan());
            }
            case 1 -> {
                members.add(PassengerFactory.createWoman());
            }
            case 2 -> {
                members.add(PassengerFactory.createMan());
                members.add(PassengerFactory.createWoman());
            }
            case 3 -> {
                members.add(PassengerFactory.createMan());
                members.add(PassengerFactory.createMan());
            }
            case 4 -> {
                members.add(PassengerFactory.createWoman());
                members.add(PassengerFactory.createWoman());
            }
            case 5 -> {
                members.add(PassengerFactory.createMan());
                members.add(PassengerFactory.createChild());
            }
            case 6 -> {
                members.add(PassengerFactory.createWoman());
                members.add(PassengerFactory.createChild());
            }
            case 7 -> {
                members.add(PassengerFactory.createMan());
                members.add(PassengerFactory.createWoman());
                members.add(PassengerFactory.createChild());
            }
        }

        return members;
    }

}
