package com.airportsim.view.ui.panels;

import javax.swing.*;
import java.awt.*;

public class SteuerPanel extends JPanel {

    private final JButton btnStart, btnPause, btnStopp, btnExit;

    public SteuerPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200, 0)); // Fixe Breite für SplitPane

        // Leerer Bereich oben (kann später z. B. Statusinfos enthalten)
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(Box.createVerticalGlue()); // Flexibler Abstand

        add(topPanel, BorderLayout.CENTER);

        // Button-Leiste unten
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(4, 1, 5, 5));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btnStart = new JButton("Start");
        btnPause = new JButton("Pause");
        btnStopp = new JButton("Stopp");
        btnExit = new JButton("Exit");

        bottomPanel.add(btnStart);
        bottomPanel.add(btnPause);
        bottomPanel.add(btnStopp);
        bottomPanel.add(btnExit);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Getter für Buttons
    public JButton getBtnStart() { return btnStart; }
    public JButton getBtnPause() { return btnPause; }
    public JButton getBtnStopp() { return btnStopp; }
    public JButton getBtnExit() { return btnExit; }
}
