package org.app.Models.Entities;

import org.app.Models.DAO.Person.ReservationDAO;
import org.app.Models.Helpers.LevenshteinDistance;
import org.views.client.reservation.ReservationView;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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

    public void setPartnerId(UUID partnerId) {
        this.partnerId = partnerId;
    }

    public UUID getPartnerId() {
        return partnerId;
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

    public static void getReservationById(Person person) throws SQLException {
        int id = reservationView.getReservationId();
        Reservation reservation = reservationDAO.findById(id, person.getId());
        reservationView.displayReservation(reservation);

    }

    public static void getAllReservations(Person person) throws SQLException {
        List<Reservation> reservations = reservationDAO.getAllReservations(person.getId());
        reservationView.displayReservationsList(reservations);
    }

    public static void addReservation(Person person) throws SQLException{
        List<Object> ticketFilters = reservationView.addReservationSpecifications();
        List<List<Object>> tickets = reservationDAO.findTicketsByFilters(ticketFilters);
        reservationView.getTickets(tickets);

        List<Object> chosenTicket = reservationView.ReserveTicket(tickets);
        Optional ticketOptional = Optional.ofNullable(chosenTicket);
        if(ticketOptional != null && ticketOptional.isPresent() && !ticketOptional.isEmpty()) {
            System.out.println(chosenTicket);
            int id = reservationDAO.getLastId()+1;
            String ticketID = chosenTicket.get(0).toString();
            String partnerID = chosenTicket.get(7).toString();
            Reservation reservation = new Reservation(id, LocalDateTime.now(), person.getId(), UUID.fromString(ticketID), UUID.fromString(partnerID), null);
            reservationDAO.save(reservation);
            System.out.println("Reservation Saved Successfully!!");
        } else {
            System.out.println("There is No Ticket For Your Specifications Currently!!");
        }

    }
/*
    public static void updatePerson() throws SQLException {
        Person updatePerson = personView.addPerson();
        personDAO.update(updatePerson);
    }*/

    public static void cancelReservation(Person person) throws SQLException {
        int reservationId = reservationView.cancelReservation(person);
        if(reservationId != 0 && LevenshteinDistance.confirmCancellation()) {
            reservationDAO.cancel(reservationId);
            reservationView.cancellationDone();
        } else {
            System.out.println("Deletion Process Incomplete!");
        }

    }
}
