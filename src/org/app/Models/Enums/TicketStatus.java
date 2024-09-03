package org.app.Models.Enums;

public enum TicketStatus {

    SOLD("SOLD"),
    CANCELED("CANCELED"),
    WAITING("WAITING");

    private String ticketStatus;

    TicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public String getTicketStatus() {
        return this.ticketStatus;
    }

    public static TicketStatus fromString(String ticketStatus) {
        for (TicketStatus t : TicketStatus.values()) {
            if (t.getTicketStatus().equalsIgnoreCase(ticketStatus)) {
                return t;
            }
        }
        throw new IllegalArgumentException("No enum constant for currentStatus: " + ticketStatus);
    }
}
