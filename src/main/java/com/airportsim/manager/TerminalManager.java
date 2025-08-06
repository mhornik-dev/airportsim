package com.airportsim.manager;

import com.airportsim.controller.TerminalController;
import java.util.List;

public class TerminalManager extends AbstractAreaManager<TerminalController> {
    public TerminalManager(List<TerminalController> controllers) {
        super(controllers, "TerminalManager");
    }
}
