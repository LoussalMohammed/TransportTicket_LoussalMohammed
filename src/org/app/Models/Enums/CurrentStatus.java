package org.app.Models.Enums;

public enum CurrentStatus {
    IN_PROGRESS("IN_PROGRESS"),
    ENDED("ENDED"),
    SUSPENDED("SUSPENDED");

    private String currentStatus;

    CurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getCurrentStatus() {
        return this.currentStatus;
    }

    public static CurrentStatus fromString(String currentStatus) {
        for (CurrentStatus c : CurrentStatus.values()) {
            if (c.getCurrentStatus().equalsIgnoreCase(currentStatus)) {
                return c;
            }
        }
        throw new IllegalArgumentException("No enum constant for currentStatus: " + currentStatus);
    }
}
