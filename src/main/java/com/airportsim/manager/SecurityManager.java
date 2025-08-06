package com.airportsim.manager;

import com.airportsim.controller.SecurityController;
import java.util.List;


public class SecurityManager extends AbstractStationManager<SecurityController> {
    public SecurityManager(List<SecurityController> controllers) {
        super(controllers, "Security");
    }
}
