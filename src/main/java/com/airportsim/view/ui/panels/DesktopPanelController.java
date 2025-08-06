package com.airportsim.view.ui.panels;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.util.*;

public class DesktopPanelController {

    private final DesktopPanel desktopPanel;
    private Map<String, JInternalFrame> internalFrameMap;

    public DesktopPanelController() {
        this.desktopPanel = new DesktopPanel();
        this.internalFrameMap = new HashMap<>();
    }

    public DesktopPanel getPanel() {
        return desktopPanel;
    }

    public void createInternalFrame(String title, JPanel content, int x, int y) {
        JInternalFrame frame = new JInternalFrame(title, true, true, true, true);
        frame.setSize(300, 200);
        frame.setLocation(x, y);
        frame.setContentPane(content);
        frame.setVisible(true);

        // Entfernt den Frame aus der Map, wenn er geschlossen wird
        frame.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                internalFrameMap.remove(title);
            }
        });

        // Schließen aktivieren (standardmäßig nur verstecken)
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        desktopPanel.add(frame);
        desktopPanel.repaint();

        internalFrameMap.put(title, frame);
    }

    public JInternalFrame getInternalFrame(String title) {
        return internalFrameMap.get(title);
    }

    public void removeInternalFrame(String title) {
        JInternalFrame frame = internalFrameMap.remove(title);
        if (frame != null) {
            frame.dispose(); // Schließt das Frame
            desktopPanel.repaint();
        }
    }

    public Collection<JInternalFrame> getAllInternalFrames() {
        return internalFrameMap.values();
    }

    public boolean hasFrame(String title) {
        return internalFrameMap.containsKey(title);
    }
}


