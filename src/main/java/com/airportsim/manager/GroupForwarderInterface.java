package com.airportsim.manager;

import com.airportsim.model.entities.Group;
import com.airportsim.model.entities.Passenger;

public interface GroupForwarderInterface {
    void forwardGroup(Group<Passenger> group);
}

