package org.app.Models.Enums;

public enum OfferStatus {

    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    SUSPENDED("SUSPENDED");

    private String offerStatus;

    OfferStatus(String offerStatus) {
        this.offerStatus = offerStatus;
    }

    public String getOfferStatus() {
        return this.offerStatus;
    }

    public static OfferStatus fromString(String offerStatus) {
        for (OfferStatus o : OfferStatus.values()) {
            if (o.getOfferStatus().equalsIgnoreCase(offerStatus)) {
                return o;
            }
        }
        throw new IllegalArgumentException("No enum constant for currentStatus: " + offerStatus);
    }
}
