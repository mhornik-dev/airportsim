package com.airportsim.view.ui.panels;


import javax.swing.text.*;
import javax.swing.*;
import java.awt.*;


public class EventOutputPanelController {

    private final FacilityEventPanel facilityEventPanel;
    private final GroupEventPanel groupEventPanel;
    private final PassengerEventPanel passengerEventPanel;

    public EventOutputPanelController() {
        this.facilityEventPanel = new FacilityEventPanel();
        this.groupEventPanel = new GroupEventPanel();
        this.passengerEventPanel = new PassengerEventPanel();
    }

    public FacilityEventPanel getFacilityPanel() {
        return facilityEventPanel;
    }

    public GroupEventPanel getGroupPanel() {
        return groupEventPanel;
    }

    public PassengerEventPanel getPassengerPanel() {
        return passengerEventPanel;
    }

    public void appendFacilityEvent(String message, Color color) {
        appendFacilityEvent(message, color, 12); // Standardgröße 12
    }

    public void appendFacilityEvent(String message, Color color, int fontSize) {
        JTextPane textPane = facilityEventPanel.getTextPane();
        StyledDocument doc = textPane.getStyledDocument();

        Style style = textPane.addStyle("Style", null);
        StyleConstants.setForeground(style, color);
        StyleConstants.setFontSize(style, fontSize);

        try {
            doc.insertString(doc.getLength(), message + "\n", style);
            textPane.setCaretPosition(doc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public void appendGroupEvent(String message, Color color) {
        appendGroupEvent(message, color, 12); // Standardgröße 12
    }

    public void appendGroupEvent(String message, Color color, int fontSize) {
        JTextPane textPane = groupEventPanel.getTextPane();
        StyledDocument doc = textPane.getStyledDocument();

        Style style = textPane.addStyle("Style", null);
        StyleConstants.setForeground(style, color);
        StyleConstants.setFontSize(style, fontSize);

        try {
            doc.insertString(doc.getLength(), message + "\n", style);
            textPane.setCaretPosition(doc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public void appendPassengerEvent(String message, Color color) {
        appendPassengerEvent(message, color, 12); // Standardgröße 12
    }

    public void appendPassengerEvent(String message, Color color, int fontSize) {
        JTextPane textPane = passengerEventPanel.getTextPane();
        StyledDocument doc = textPane.getStyledDocument();

        Style style = textPane.addStyle("Style", null);
        StyleConstants.setForeground(style, color);
        StyleConstants.setFontSize(style, fontSize);

        try {
            doc.insertString(doc.getLength(), message + "\n", style);
            textPane.setCaretPosition(doc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}

