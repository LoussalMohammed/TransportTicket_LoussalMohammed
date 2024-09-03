package org.app.Models.Enums;

public enum ReductionType {

    PERCENTAGE("PERCENTAGE"),
    FIXED_PRICE("FIXED-PRICE");

    private String reductionType;

    ReductionType(String reductionType) {
        this.reductionType = reductionType;
    }

    public String getReductionType() {
        return this.reductionType;
    }

    public static ReductionType fromString(String reductionType) {
        for (ReductionType r : ReductionType.values()) {
            if (r.getReductionType().equalsIgnoreCase(reductionType)) {
                return r;
            }
        }
        throw new IllegalArgumentException("No enum constant for currentStatus: " + reductionType);
    }
}
