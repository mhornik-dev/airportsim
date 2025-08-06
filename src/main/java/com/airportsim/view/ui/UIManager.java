package com.airportsim.view.ui;

import com.airportsim.view.ui.panels.*;

public class UIManager {

    private SteuerPanelController steuerPanelController;
    private DesktopPanelController desktopPanelController;
    private EventOutputPanelController facilityEventController;
    private EventOutputPanelController groupEventController;
    private EventOutputPanelController passengerEventController;

    private MainUI mainUI;

    public UIManager() {
        initMainUI();
        mainUI.setVisible(true); // MainUI sichtbar machen

        
    }

    public MainUI initMainUI() {
        // Controller initialisieren
        steuerPanelController = new SteuerPanelController(this);
        desktopPanelController = new DesktopPanelController();
        facilityEventController = new EventOutputPanelController();
        groupEventController = new EventOutputPanelController();
        passengerEventController = new EventOutputPanelController();

        // MainUI mit den Panels bauen (angenommen MainUI hat passenden Konstruktor)
        mainUI = new MainUI(
            steuerPanelController.getPanel(),
            desktopPanelController.getPanel(),
            facilityEventController.getFacilityPanel(),
            groupEventController.getGroupPanel(),
            passengerEventController.getPassengerPanel()
        );

        // Hier ggf. Listener verbinden, Start-Logik etc.

        return mainUI;
    }

    // Optional: Getter für Controller falls woanders Steuerung benötigt wird
    public SteuerPanelController getSteuerPanelController() {
        return steuerPanelController;
    }

    public DesktopPanelController getDesktopPanelController() {
        return desktopPanelController;
    }

    public EventOutputPanelController getFacilityEventController() {
        return facilityEventController;
    }

    public EventOutputPanelController getGroupEventController() {
        return groupEventController;
    }

    public EventOutputPanelController getPassengerEventController() {
        return passengerEventController;
    }
}

