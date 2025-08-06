package com.airportsim.model.entities;


public class CheckinStation extends Station {
    private Group<Passenger> currentGroup;
    private Passenger currentPerson;

    public CheckinStation(Long id) {
        super(id, StationType.CHECKIN);
    }

    public Group<Passenger> getCurrentGroup() {
        return currentGroup;
    }

    public void setCurrentGroup(Group<Passenger> currentGroup) {
        this.currentGroup = currentGroup;
    }

    public Passenger getCurrentPerson() {
        return currentPerson;
    }

    public void setCurrentPerson(Passenger currentPerson) {
        this.currentPerson = currentPerson;
    }

}
