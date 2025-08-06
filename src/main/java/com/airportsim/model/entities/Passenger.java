package com.airportsim.model.entities;

import java.util.List;
import java.util.ArrayList;

public abstract class Passenger {
    private final Long id;
    private final PersonType type;
    private final List<Luggage> luggage;

    protected Passenger(Long id, PersonType type) {
        this.id = id;
        this.type = type;
        this.luggage = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public PersonType getType() {
        return type;
    }

    public List<Luggage> getLuggage() {
        return luggage;
    }

    public void addLuggage(List<Luggage> luggage) {
        this.luggage.addAll(luggage);
    }

    @Override
    public String toString() {
        return "Person{" +
                "type=" + type +
                '}';
    }
    public enum PersonType {
        MAN, WOMAN, CHILD
    }

}
