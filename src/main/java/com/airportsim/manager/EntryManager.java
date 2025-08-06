package com.airportsim.manager;

import com.airportsim.controller.EntryController;
import java.util.List;

public class EntryManager extends AbstractAreaManager<EntryController> {
    public EntryManager(List<EntryController> controllers) {
        super(controllers, "EntryManager");
    }
}
