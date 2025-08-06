package com.airportsim.view.ui.panels;

import javax.swing.*;
import java.awt.*;

public class EventOutputPanel extends JPanel {

    private final JTextPane tpOutput;

    public EventOutputPanel() {
        setLayout(new BorderLayout());

        tpOutput = new JTextPane();
        tpOutput.setEditable(false);
        tpOutput.setFocusable(false);
        tpOutput.setBackground(Color.WHITE);
        tpOutput.setMargin(new Insets(5, 5, 5, 5));

        JScrollPane scrollPane = new JScrollPane(tpOutput);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(scrollPane, BorderLayout.CENTER);
    }

    public JTextPane getTextPane() {
        return tpOutput;
    }
}

