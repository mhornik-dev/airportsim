package com.airportsim.model.entities;

import java.util.List;

import com.airportsim.model.enums.TerminalNames;

import java.util.ArrayList;

public class TerminalArea extends Area {
    private TerminalNames name;
    private List<Group<Passenger>> currentGroups;
    
    public TerminalArea(Long id, TerminalNames name) {
        super(id, AreaType.TERMINAL);
        this.name = name;
        this.currentGroups = new ArrayList<>();
    }

    public TerminalNames getName() {
        return name;
    }

    public synchronized List<Group<Passenger>> getCurrentGroups() {
        return new ArrayList<>(currentGroups);
    }

    public synchronized void addGroup(Group<Passenger> group) {
        this.currentGroups.add(group);
    }

    public synchronized void removeGroup(Group<Passenger> group) {
        this.currentGroups.remove(group);
    }

}
