package com.airportsim.manager;

import com.airportsim.model.entities.Group;
import com.airportsim.model.entities.Passenger;

public interface GroupProviderInterface {
    Group<Passenger> getNextGroup() throws InterruptedException;
    void onGroupTaken();
}
