package com.airportsim.model.entities;

import java.time.LocalTime;

public class SimulationTime {
    private final int totalSeconds; // Sekunden seit Mitternacht (0–86399)

    public SimulationTime(int totalSeconds) {
        this.totalSeconds = totalSeconds % (24 * 60 * 60); // 86400 Sekunden pro Tag
    }

    public SimulationTime(LocalTime localTime) {
        this(localTime.toSecondOfDay());
    }

    public int getHour() {
        return totalSeconds / 3600;
    }

    public int getMinute() {
        return (totalSeconds % 3600) / 60;
    }

    public int getSecond() {
        return totalSeconds % 60;
    }

    public int getTotalSeconds() {
        return totalSeconds;
    }

    public String getTimeString() {
        return String.format("%02d:%02d:%02d", getHour(), getMinute(), getSecond());
    }

    public LocalTime toLocalTime() {
        return LocalTime.of(getHour(), getMinute(), getSecond());
    }

    @Override
    public String toString() {
        return getTimeString();
    }
}
