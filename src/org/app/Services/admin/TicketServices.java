package org.app.Services.Admin;

import org.app.Models.Entities.Ticket;
import org.app.Models.Enums.TicketStatus;
import org.app.Models.Enums.TransportType;
import org.app.tools.databaseC;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TicketServices {

    public String delete(UUID id) throws SQLException {
        String sql = "DELETE FROM ticket WHERE id = ?";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);

            int rowsAffected = statement.executeUpdate(); // Use executeUpdate() for DELETE

            if (rowsAffected > 0) {
                return "Ticket Deleted Successfully!";
            } else {
                return "Ticket Haven't Been Deleted!";
            }
        }
    }

    public Ticket findById(UUID id) throws SQLException {
        try (Connection connection = databaseC.getInstance().getConnection()) {
            String sql = "SELECT * FROM ticket WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return new Ticket(
                            (UUID) resultSet.getObject("id"),
                            TransportType.fromString(resultSet.getString("transportType")),
                            resultSet.getBigDecimal("buyingPrice"),
                            resultSet.getBigDecimal("sellingPrice"),
                            resultSet.getDate("sellingDate"),
                            TicketStatus.fromString(resultSet.getString("ticketStatus"))
                    );
                } else {
                    return null;
                }
            }
        }
    }

    public List<Ticket> getTickets() throws SQLException {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = databaseC.getInstance().getConnection()) {
            String sql = "SELECT * FROM ticket";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tickets.add(new Ticket(
                        (UUID) resultSet.getObject("id"),
                        TransportType.fromString(resultSet.getString("transportType")),
                        resultSet.getBigDecimal("buyingPrice"),
                        resultSet.getBigDecimal("sellingPrice"),
                        resultSet.getDate("sellingDate"),
                        TicketStatus.fromString(resultSet.getString("ticketStatus"))
                ));
            }
        }
        return tickets;
    }

    public void save(Ticket ticket) throws SQLException {
        String sql = "INSERT INTO ticket (id, transportType, buyingPrice, sellingPrice, sellingDate, ticketStatus) " +
                "VALUES (?, CAST(? AS transportType), ?, ?, ?, CAST(? AS ticketStatus))";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, ticket.getId());
            statement.setObject(2, ticket.getTransportType().name(), Types.OTHER);
            statement.setBigDecimal(3, ticket.getBuyingPrice());
            statement.setBigDecimal(4, ticket.getSellingPrice());
            statement.setDate(5, new java.sql.Date(ticket.getSellingDate().getTime()));
            statement.setObject(6, ticket.getTicketStatus().name(), Types.OTHER);

            statement.executeUpdate();
        }
    }

    public void update(Ticket ticket) throws SQLException {
        String sql = "UPDATE ticket SET transportType = ?, buyingPrice = ?, sellingPrice = ?, sellingDate = ?, " +
                "ticketStatus = ? WHERE id = ?";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, ticket.getTransportType().name(), Types.OTHER);
            statement.setBigDecimal(2, ticket.getBuyingPrice());
            statement.setBigDecimal(3, ticket.getSellingPrice());
            statement.setDate(4, new java.sql.Date(ticket.getSellingDate().getTime()));
            statement.setObject(5, ticket.getTicketStatus().name(), Types.OTHER);
            statement.setObject(6, ticket.getId());

            statement.executeUpdate();
        }
    }
}
