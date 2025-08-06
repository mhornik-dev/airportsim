package com.airportsim.model.events;

import com.airportsim.model.entities.SimulationTime;
import com.airportsim.model.entities.Group;
import com.airportsim.model.entities.Passenger;

public class GroupEvent extends LogEvent<Group<Passenger>> {

    private final Long groupId;

    public GroupEvent(Group<Passenger> group, EventType type, SimulationTime time, String note) {
        super(type, time, note);
        this.groupId = group.getId();
    }

    public Long getGroupId() {
        return groupId;
    }

    @Override
    public String toString() {
        return "GroupID: " + getGroupId() + " - " + super.toString();
    }

}
