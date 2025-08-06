package com.airportsim.view.ui.panels;

import javax.swing.*;
import java.awt.event.ActionListener;
import com.airportsim.view.ui.UIManager;

public class SteuerPanelController {

    private final SteuerPanel steuerPanel;
    private final UIManager uiManager;

    public SteuerPanelController(UIManager uiManager) {
        this.steuerPanel = new SteuerPanel();
        this.uiManager = uiManager;
        initListeners();
    }

    private void initListeners() {
        // Standardaktionen (kann leer bleiben oder z. B. Logging enthalten)
        steuerPanel.getBtnStart().addActionListener(e -> onStart());
        steuerPanel.getBtnPause().addActionListener(e -> onPause());
        steuerPanel.getBtnStopp().addActionListener(e -> onStopp());
        steuerPanel.getBtnExit().addActionListener(e -> onExit());
    }

    public SteuerPanel getPanel() {
        return steuerPanel;
    }

    // Beispielmethoden für eigene Aktionen
    private void onStart() {
        System.out.println("Start gedrückt");
        // Start-Logik hier integrieren
    }

    private void onPause() {
        System.out.println("Pause gedrückt");
        // Pause-Logik hier integrieren
    }

    private void onStopp() {
        System.out.println("Stopp gedrückt");
        // Stopp-Logik hier integrieren
    }

    private void onExit() {
        System.out.println("Exit gedrückt");
        // Bestätigen + Applikation beenden:
        int result = JOptionPane.showConfirmDialog(
            steuerPanel,
            "Simulation wirklich beenden?",
            "Beenden bestätigen",
            JOptionPane.YES_NO_OPTION
        );
        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    // Optional: Externe Möglichkeit, Listener zu setzen
    public void setStartAction(ActionListener listener) {
        steuerPanel.getBtnStart().addActionListener(listener);
    }

    public void setPauseAction(ActionListener listener) {
        steuerPanel.getBtnPause().addActionListener(listener);
    }

    public void setStoppAction(ActionListener listener) {
        steuerPanel.getBtnStopp().addActionListener(listener);
    }

    public void setExitAction(ActionListener listener) {
        steuerPanel.getBtnExit().addActionListener(listener);
    }
}

