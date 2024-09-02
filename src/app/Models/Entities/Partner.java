package app.Models.Entities;

import app.Models.enums.PartenaryStatus;
import app.Models.enums.TransportType;

import java.util.Date;
import java.util.UUID;

public class Partner {
    private UUID id;
    private String companyName;
    private String commercialContact;
    private TransportType transportType;
    private String geographicZone;
    private String specialCondition;
    private PartenaryStatus partenaryStatus;
    private Date createdAt;

    // Constructor
    public Partner(UUID id, String companyName, String commercialContact,
                   TransportType transportType, String geographicZone,
                   String specialCondition, PartenaryStatus partenaryStatus,
                   Date createdAt) {
        this.id = id;
        this.companyName = companyName;
        this.commercialContact = commercialContact;
        this.transportType = transportType;
        this.geographicZone = geographicZone;
        this.specialCondition = specialCondition;
        this.partenaryStatus = partenaryStatus;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCommercialContact() {
        return commercialContact;
    }

    public void setCommercialContact(String commercialContact) {
        this.commercialContact = commercialContact;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

    public String getGeographicZone() {
        return geographicZone;
    }

    public void setGeographicZone(String geographicZone) {
        this.geographicZone = geographicZone;
    }

    public String getSpecialCondition() {
        return specialCondition;
    }

    public void setSpecialCondition(String specialCondition) {
        this.specialCondition = specialCondition;
    }

    public PartenaryStatus getPartenaryStatus() {
        return partenaryStatus;
    }

    public void setPartenaryStatus(PartenaryStatus partenaryStatus) {
        this.partenaryStatus = partenaryStatus;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", Company Name='" + companyName + '\'' +
                ", Commercial Contact='" + commercialContact + '\'' +
                ", Transport Type='" + transportType + '\'' +
                ", Geographic Zone='" + geographicZone + '\'' +
                ", Special Condition=" + specialCondition +
                ", Partenary Status=" + partenaryStatus +
                ", Created At=" + createdAt +
                '}';
    }

}
