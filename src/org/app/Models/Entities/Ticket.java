package org.app.Models.Entities;

import java.math.BigDecimal;
import java.util.UUID;
import org.app.Models.Enums.TransportType;
import java.util.Date;
import org.app.Models.Enums.TicketStatus;

public class Ticket {
    private UUID id;
    private TransportType transportType;
    private BigDecimal buyingPrice;
    private BigDecimal sellingPrice;
    private Date sellingDate;
    private TicketStatus ticketStatus;

    // Constructor
    public Ticket(UUID id, TransportType transportType, BigDecimal buyingPrice,
                  BigDecimal sellingPrice, Date sellingDate, TicketStatus ticketStatus) {
        this.id = id;
        this.transportType = transportType;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.sellingDate = sellingDate;
        this.ticketStatus = ticketStatus;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

    public BigDecimal getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(BigDecimal buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Date getSellingDate() {
        return sellingDate;
    }

    public void setSellingDate(Date sellingDate) {
        this.sellingDate = sellingDate;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", Transport Type='" + transportType + '\'' +
                ", Buying Price='" + buyingPrice + '\'' +
                ", Selling Price='" + sellingPrice + '\'' +
                ", Selling Date='" + sellingDate + '\'' +
                ", Ticket Status=" + ticketStatus +
                '}';
    }
}
