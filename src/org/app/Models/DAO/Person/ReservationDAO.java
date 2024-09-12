package org.app.Models.DAO.Person;

import org.app.Models.Entities.Reservation;
import org.app.Models.Entities.Ticket;
import org.app.Models.Enums.Transport;
import org.app.tools.databaseC;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReservationDAO {

    public void delete(int id) throws SQLException {
        String sql = "UPDATE reservations SET deleted_at = ? WHERE id = ?";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, Date.valueOf(LocalDateTime.now().toLocalDate()));
            statement.setInt(2, id);

            statement.executeUpdate();
        }
    }

    public void restore(int id) throws SQLException {
        String sql = "UPDATE reservations SET deleted_at = ? WHERE id = ?";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, null);
            statement.setInt(2, id);

            statement.executeUpdate();
        }
    }

    public List<List<Object>> findTicketsByFilters(List<Object> ticketFilters) throws SQLException {
        List<List<Object>> tickets = new ArrayList<>();
        String sql = "SELECT tickets.*, routes.price, routes.partnerId, routes.departureDate " +
                "FROM tickets " +
                "LEFT JOIN routes ON tickets.routeId = routes.id " +
                "WHERE levenshtein(routes.departureCity, ?) <= 2 " +
                "AND levenshtein(routes.destinationCity, ?) <= 2 " +
                "AND routes.departureDate = ?";

        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, ticketFilters.get(0)); // departureCity
            statement.setObject(2, ticketFilters.get(1)); // destinationCity
            statement.setObject(3, ticketFilters.get(2)); // departureDate


            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    List<Object> ticket = new ArrayList<>();
                    ticket.add(Transport.fromString(resultSet.getString("transportType")));
                    ticket.add(resultSet.getString("transporter"));
                    ticket.add(resultSet.getDate("departureDate").toLocalDate().atStartOfDay());
                    ticket.add(resultSet.getBigDecimal("price"));
                    ticket.add(resultSet.getObject("partnerId"));

                    tickets.add(ticket);
                }
            }
        }
        return tickets;
    }


    public Reservation findById(int id) throws SQLException {
        try (Connection connection = databaseC.getInstance().getConnection()) {
            String sql = "SELECT * FROM reservations WHERE id = ? AND deleted_at IS NULL";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return new Reservation(
                            resultSet.getInt("id"),
                            resultSet.getObject("reserved_at", LocalDateTime.class),
                            resultSet.getInt("clientId"),
                            (UUID) resultSet.getObject("ticketId"),
                            (UUID) resultSet.getObject("partnerId"),
                            resultSet.getObject("canceled_at", LocalDateTime.class)
                    );
                } else {
                    return null;
                }
            }
        }
    }

    public List<Reservation> getAllReservations() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        try (Connection connection = databaseC.getInstance().getConnection()) {
            String sql = "SELECT * FROM reservations WHERE deleted_at IS NULL";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                reservations.add(new Reservation(
                        resultSet.getInt("id"),
                        resultSet.getObject("reserved_at", LocalDateTime.class),
                        resultSet.getInt("clientId"),
                        (UUID) resultSet.getObject("ticketId"),
                        (UUID) resultSet.getObject("partnerId"),
                        resultSet.getObject("canceled_at", LocalDateTime.class)
                ));
            }
        }
        return reservations;
    }

    public void save(Reservation reservation) throws SQLException {
        String sql = "INSERT INTO reservations (id, reserved_at, clientId, ticketId) VALUES (?, ?, ?, ?)";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, reservation.getId());
            statement.setObject(2, reservation.getReservedAt());
            statement.setInt(3, reservation.getClientId());
            statement.setObject(4, reservation.getTicketId());

            statement.executeUpdate();
        }
    }

    public void update(Reservation reservation) throws SQLException {
        String sql = "UPDATE reservations SET reserved_at = ?, clientId = ?, ticketId = ?, canceled_at = ? WHERE id = ?";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, reservation.getReservedAt());
            statement.setInt(2, reservation.getClientId());
            statement.setObject(3, reservation.getTicketId());
            statement.setObject(4, reservation.getCanceledAt());
            statement.setInt(5, reservation.getId());

            statement.executeUpdate();
        }
    }

}
