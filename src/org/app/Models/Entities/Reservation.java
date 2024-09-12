package org.app.Models.Entities;

import org.app.Models.DAO.Person.ReservationDAO;
import org.app.Models.Helpers.LevenshteinDistance;
import org.views.client.reservation.ReservationView;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Reservation {

    private static ReservationView reservationView = new ReservationView();
    private static ReservationDAO reservationDAO = new ReservationDAO();

    private int id;
    private LocalDateTime reservedAt;
    private LocalDateTime canceledAt;
    private int clientId;
    private UUID ticketId;
    private UUID partnerId;

    public Reservation(int id, LocalDateTime reservedAt, int clientId, UUID ticketId, UUID partnerId, LocalDateTime canceledAt) {
        this.id = id;
        this.reservedAt = reservedAt;
        this.clientId = clientId;
        this.ticketId = ticketId;
        this.partnerId = partnerId;
        this.canceledAt = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getReservedAt() {
        return reservedAt;
    }

    public void setReservedAt(LocalDateTime reservedAt) {
        this.reservedAt = reservedAt;
    }

    public LocalDateTime getCanceledAt() {
        return canceledAt;
    }

    public void setCanceledAt(LocalDateTime canceledAt) {
        this.canceledAt = canceledAt;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public UUID getTicketId() {
        return ticketId;
    }

    public void setTicketId(UUID ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", reservedAt=" + reservedAt +
                ", canceledAt=" + canceledAt +
                ", clientId=" + clientId +
                ", ticketId=" + ticketId +
                '}';
    }

    public static void getReservationById() throws SQLException {
        int id = reservationView.getReservationId();
        Reservation reservation = reservationDAO.findById(id);
        reservationView.displayReservation(reservation);
    }

    public static void getAllReservations() throws SQLException {
        List<Reservation> reservations = reservationDAO.getAllReservations();
        reservationView.displayReservationsList(reservations);
    }

    public static void addReservation() throws SQLException{
        List<Object> ticketFilters = reservationView.createReservation();
        List<List<Object>> tickets = reservationDAO.findTicketsByFilters(ticketFilters);
        reservationView.getTickets(tickets);
    }
/*
    public static void updatePerson() throws SQLException {
        Person updatePerson = personView.addPerson();
        personDAO.update(updatePerson);
    }

    public static void deletePerson() throws SQLException {
        int deletedPersonId = personView.getPerson();
        if(LevenshteinDistance.confirmDeletion()) {
            personDAO.delete(deletedPersonId);
        }

    }*/
}
