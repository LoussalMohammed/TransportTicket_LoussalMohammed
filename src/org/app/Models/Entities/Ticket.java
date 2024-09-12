package org.app.Models.Entities;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.app.Models.DAO.Admin.TicketDAO;
import org.app.Models.Enums.Transport;
import java.util.Date;
import org.app.Models.Enums.TicketStatus;
import org.app.Models.DAO.Admin.TicketDAO;
import org.app.Models.Helpers.LevenshteinDistance;
import org.views.admin.ticket.TicketView;

public class Ticket {
    private static TicketDAO ticketDAO = new TicketDAO();
    private static TicketView ticketView = new TicketView();
    private static Scanner scanner = new Scanner(System.in);
    private UUID id;
    private Transport transportType;
    private BigDecimal buyingPrice;
    private BigDecimal sellingPrice;
    private Date sellingDate;
    private TicketStatus ticketStatus;

    private UUID contract_id;

    private String transporter;
    // Constructor
    public Ticket(UUID id, Transport transportType, BigDecimal buyingPrice,
                  BigDecimal sellingPrice, Date sellingDate, TicketStatus ticketStatus, UUID contract_id, String transporter) {
        this.id = id;
        this.transportType = transportType;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.sellingDate = sellingDate;
        this.ticketStatus = ticketStatus;
        this.contract_id = contract_id;
        this.transporter = transporter;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Transport getTransportType() {
        return transportType;
    }

    public void setTransportType(Transport transportType) {
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

    public UUID getContract_id() {
        return this.contract_id;
    }

    public void setContract_id(UUID contract_id) {
        this.contract_id = contract_id;
    }

    public String getTransporter() {
        return transporter;
    }

    public void setTransporter(String transporter) {
        this.transporter = transporter;
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

    public static void getTicketById() throws SQLException {
        java.util.UUID ticketID = ticketView.getTicketId();
        Ticket ticket = ticketDAO.findById(ticketID);
        ticketView.displayTicket(ticket);
    }

    public static void getAllTickets() throws SQLException {
        List<Ticket> tickets = ticketDAO.getTickets();
        ticketView.displayTicketsList(tickets);
    }

    public static void addTicket() throws SQLException {
        Ticket ticket = ticketView.addTicket();
        ticketDAO.save(ticket);
    }

    public static void updateTicket() {
        ticketView.updateTicket();
    }

    public static void deleteTicket() throws SQLException {
        System.out.print("Enter ticket ID to delete (UUID format): ");
        UUID deleteTicketId = UUID.fromString(scanner.nextLine().trim());
        TicketDAO ticketDAO1 = new TicketDAO();
        Ticket ticketToDelete = ticketDAO1.findById(deleteTicketId);
        if (ticketToDelete != null && LevenshteinDistance.confirmDeletion()) {
            ticketDAO1.delete(deleteTicketId);
            System.out.println("Ticket successfully deleted.");
        }
    }
    public static void restoreTicket() throws SQLException {
        System.out.print("Enter ticket ID to restore (UUID format): ");
        UUID restoreTicketId = UUID.fromString(scanner.nextLine().trim());
        ticketDAO.restore(restoreTicketId);
        System.out.println("Ticket successfully restored.");
    }
}
