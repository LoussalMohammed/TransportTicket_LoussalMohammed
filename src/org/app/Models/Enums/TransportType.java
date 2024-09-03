package org.app.Models.Enums;

public enum TransportType {
    BUS("bus"),
    TRAIN("train"),
    AIRPLANE("airplane");

    private String transport;

    TransportType(String Transport) {
        this.transport = Transport;
    }

    public String getTransport() {
        return this.transport;
    }

    public static TransportType fromString(String transport) {
        for (TransportType t : TransportType.values()) {
            if (t.getTransport().equalsIgnoreCase(transport)) {
                return t;
            }
        }
        throw new IllegalArgumentException("No enum constant for currentStatus: " + transport);
    }
}
