package com.airportsim.model.entities;

import java.util.Set;
import java.util.HashSet;

import com.airportsim.model.enums.EntryNames;

public class EntryArea extends Area {
    private EntryNames name;
    private Set<Group<Passenger>> currentGroups;

    public EntryArea(Long id, EntryNames name) {
        super(id, AreaType.ENTRY);
        this.name = name;
        this.currentGroups = new HashSet<>();
    }

    public EntryNames getName() {
        return name;
    }

    public synchronized Set<Group<Passenger>> getCurrentGroups() {
        return new HashSet<>(currentGroups); // defensive copy
    }

    public synchronized void addGroup(Group<Passenger> group) {
        this.currentGroups.add(group); // fügt nur hinzu, wenn nicht schon vorhanden
    }

    public synchronized void removeGroup(Group<Passenger> group) {
        this.currentGroups.remove(group);
    }
}
