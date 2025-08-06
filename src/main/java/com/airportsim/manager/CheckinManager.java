package com.airportsim.manager;

import com.airportsim.controller.CheckinController;
import java.util.List;

public class CheckinManager extends AbstractStationManager<CheckinController> {
    public CheckinManager(List<CheckinController> controllers) {
        super(controllers, "Checkin");
    }
}
