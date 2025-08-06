package com.airportsim.model.enums;

public enum EntryNames {
    ENTRY_1A("Entry Area 1A"),
    ENTRY_1B("Entry Area 1B"),
    ENTRY_1C("Entry Area 1C"),
    ENTRY_2A("Entry Area 2A"),
    ENTRY_2B("Entry Area 2B"),
    ENTRY_2C("Entry Area 2C"),
    ENTRY_3A("Entry Area 3A"),
    ENTRY_3B("Entry Area 3B"),
    ENTRY_3C("Entry Area 3C");

    private String name;

    EntryNames(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
}
