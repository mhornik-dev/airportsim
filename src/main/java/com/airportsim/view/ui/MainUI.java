package com.airportsim.view.ui;

import javax.swing.*;

public class MainUI extends JFrame {

    public MainUI(JPanel steuerPanel,
                  JDesktopPane desktopPanel,
                  JPanel facilityEventsPanel,
                  JPanel groupEventsPanel,
                  JPanel passengerEventsPanel) {

        setTitle("SimuAirport - Simulation");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Oberer Bereich: SteuerPanel links, DesktopPanel rechts
        JSplitPane topSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, steuerPanel, desktopPanel);
        topSplit.setDividerLocation(200);
        topSplit.setDividerSize(5);
        topSplit.setEnabled(false);

        // Unterer Bereich: 3-fach geteiltes Panel (Facility | Group | Passenger)
        JSplitPane lowerSplitLeft = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, facilityEventsPanel, groupEventsPanel);
        lowerSplitLeft.setDividerLocation(400);
        lowerSplitLeft.setDividerSize(5);
        lowerSplitLeft.setEnabled(false);

        JSplitPane lowerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, lowerSplitLeft, passengerEventsPanel);
        lowerSplit.setDividerLocation(800);
        lowerSplit.setDividerSize(5);
        lowerSplit.setEnabled(false);

        // Gesamter Bereich: oben + unten (vertikal gesplittet)
        JSplitPane mainSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topSplit, lowerSplit);
        mainSplit.setDividerLocation(550); // z. B. 550 oben, 250 unten
        mainSplit.setDividerSize(5);
        mainSplit.setEnabled(false);

        add(mainSplit);
    }

}

