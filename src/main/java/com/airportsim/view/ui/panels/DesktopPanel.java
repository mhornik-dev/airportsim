package com.airportsim.view.ui.panels;

import javax.swing.*;

public class DesktopPanel extends JDesktopPane {

    public DesktopPanel() {
        setBackground(UIManager.getColor("Panel.background")); // Windows-grauer Hintergrund
        setDesktopManager(new DefaultDesktopManager() {
            @Override
            public void dragFrame(JComponent f, int newX, int newY) {
                if (f instanceof JInternalFrame) {
                    JDesktopPane d = ((JInternalFrame) f).getDesktopPane();
                    if (d != null) {
                        int x = Math.max(0, Math.min(newX, d.getWidth() - f.getWidth()));
                        int y = Math.max(0, Math.min(newY, d.getHeight() - f.getHeight()));
                        super.dragFrame(f, x, y);
                        return;
                    }
                }
                super.dragFrame(f, newX, newY);
            }
        });
    }

}
