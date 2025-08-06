package com.airportsim.model.enums;

public enum TerminalNames {
    TERMINAL_1("Terminal 1"),
    TERMINAL_2("Terminal 2"),
    TERMINAL_3("Terminal 3");

    private String name;

    TerminalNames(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
