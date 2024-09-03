package org.app.Models.Enums;

public enum PartenaryStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    SUSPENDED("SUSPENDED");

    private String partenaryStatus;

    PartenaryStatus(String partenaryStatus) {
        this.partenaryStatus = partenaryStatus;
    }

    public String getPartenaryStatus() {
        return this.partenaryStatus;
    }

    public static PartenaryStatus fromString(String partenaryStatus) {
        for (PartenaryStatus p : PartenaryStatus.values()) {
            if (p.getPartenaryStatus().equalsIgnoreCase(partenaryStatus)) {
                return p;
            }
        }
        throw new IllegalArgumentException("No enum constant for currentStatus: " + partenaryStatus);
    }
}
