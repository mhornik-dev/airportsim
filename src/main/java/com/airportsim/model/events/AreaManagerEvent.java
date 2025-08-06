package com.airportsim.model.events;

import com.airportsim.model.entities.SimulationTime;
import com.airportsim.manager.AbstractAreaManager;

public class AreaManagerEvent extends LogEvent<AbstractAreaManager<?>> {

    private final String managerName;

    public AreaManagerEvent(AbstractAreaManager<?> manager, EventType type, SimulationTime time, String note) {
        super(type, time, note);
        this.managerName = manager.getManagerName();
    }

    public String getManagerName() {
        return managerName;
    }

    @Override
    public String toString() {
        return "ManagerName: " + getManagerName() + " - Eventtype: " + getType() + " - Timestamp: " + (getTime() != null ? getTime() : "N/A") +
            (getNote() != null && !getNote().isEmpty() ? " - Info: " + getNote() : "");
    }
}
