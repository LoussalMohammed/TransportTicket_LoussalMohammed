package org.app.Models.Enums;

public enum Transport {
    BUS("bus"),
    TRAIN("train"),
    AIRPLANE("airplane");

    private String transport;

    Transport(String Transport) {
        this.transport = Transport;
    }

    public String getTransport() {
        return this.transport;
    }

    public static Transport fromString(String transport) {
        for (Transport t : Transport.values()) {
            if (t.getTransport().equalsIgnoreCase(transport)) {
                return t;
            }
        }
        throw new IllegalArgumentException("No enum constant for currentStatus: " + transport);
    }
}
