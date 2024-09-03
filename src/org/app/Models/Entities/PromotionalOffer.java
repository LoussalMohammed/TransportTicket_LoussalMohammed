package concole.app.Models.Entities;

import java.util.Date;
import java.util.UUID;
import concole.app.Models.enums.ReductionType; // Assuming the enum is in the tools package
import concole.app.Models.enums.OfferStatus;   // Assuming the enum is in the tools package

public class PromotionalOffer {
    private UUID id;
    private String name;
    private String description;
    private Date initDate;
    private Date endDate;
    private ReductionType reductionType;
    private Float reductionValue;
    private String condition;
    private OfferStatus offerStatus;

    // Constructor
    public PromotionalOffer(UUID id, String name, String description,
                            Date initDate, Date endDate,
                            ReductionType reductionType, Float reductionValue,
                            String condition, OfferStatus offerStatus) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.initDate = initDate;
        this.endDate = endDate;
        this.reductionType = reductionType;
        this.reductionValue = reductionValue;
        this.condition = condition;
        this.offerStatus = offerStatus;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getInitDate() {
        return initDate;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public ReductionType getReductionType() {
        return reductionType;
    }

    public void setReductionType(ReductionType reductionType) {
        this.reductionType = reductionType;
    }

    public Float getReductionValue() {
        return reductionValue;
    }

    public void setReductionValue(Float reductionValue) {
        this.reductionValue = reductionValue;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public OfferStatus getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(OfferStatus offerStatus) {
        this.offerStatus = offerStatus;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", Name='" + name + '\'' +
                ", Description='" + description + '\'' +
                ", Init Date='" + initDate + '\'' +
                ", End Date='" + endDate + '\'' +
                ", Reduction Type=" + reductionType +
                ", Reduction Value=" + reductionValue +
                ", Condition=" + condition +
                ", Offer Status=" + offerStatus +
                '}';
    }
}