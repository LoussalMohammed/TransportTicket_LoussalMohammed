package app.Models.Entities;

import app.Models.enums.CurrentStatus;
import app.Models.enums.CurrentStatus.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
public class Contract {
    private UUID id;
    private Date initDate;
    private Date endDate;
    private BigDecimal specialTariff;
    private String accordConditions;
    private boolean renewed;
    private CurrentStatus currentStatus;

    public Contract(UUID id, Date initDate, Date endDate, BigDecimal specialTarif, String accordConditions, boolean renewed, CurrentStatus currentStatus) {
        this.id = id;
        this.initDate = initDate;
        this.endDate = endDate;
        this.specialTarif = specialTarif;
        this.accordConditions = accordConditions;
        this.renewed = renewed;
        this.currentStatus = currentStatus;
    }
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public BigDecimal getSpecialTarif() {
        return specialTarif;
    }

    public void setSpecialTarif(BigDecimal specialTarif) {
        this.specialTarif = specialTarif;
    }

    public String getAccordConditions() {
        return accordConditions;
    }

    public void setAccordConditions(String accordConditions) {
        this.accordConditions = accordConditions;
    }

    public boolean isRenewed() {
        return renewed;
    }

    public void setRenewed(boolean renewed) {
        this.renewed = renewed;
    }

    public CurrentStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(CurrentStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", Init Date='" + initDate + '\'' +
                ", End Date='" + endDate + '\'' +
                ", Special Tariff ='" + specialTariff + '\'' +
                ", Accord Conditions='" + accordConditions + '\'' +
                ", renewed=" + renewed +
                ", Current Status=" + currentStatus +
                '}';
    }
}
