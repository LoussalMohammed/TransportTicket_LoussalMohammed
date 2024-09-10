package org.app.Models.DAO.Admin;

import org.app.Models.Entities.Ticket;
import org.app.Models.Enums.TicketStatus;
import org.app.Models.Enums.Transport;
import org.app.tools.databaseC;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TicketDAO {

    public void delete(UUID id) throws SQLException {
        String sql = "UPDATE tickets SET deleted_at = ? WHERE id = ?";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, Date.valueOf(LocalDateTime.now().toLocalDate()));
            statement.setObject(2, id);

            statement.executeUpdate();
        }
    }

    public void restore(UUID id) throws SQLException {
        String sql = "UPDATE tickets SET deleted_at = ? WHERE id = ?";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, null);
            statement.setObject(2, id);

            statement.executeUpdate();
        }
    }

    public Ticket findById(UUID id) throws SQLException {
        try (Connection connection = databaseC.getInstance().getConnection()) {
            String sql = "SELECT * FROM tickets WHERE id = ? AND deleted_at IS NULL";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return new Ticket(
                            (UUID) resultSet.getObject("id"),
                            Transport.fromString(resultSet.getString("transportType")),
                            resultSet.getBigDecimal("buyingPrice"),
                            resultSet.getBigDecimal("sellingPrice"),
                            resultSet.getDate("sellingDate"),
                            TicketStatus.fromString(resultSet.getString("ticketStatus")),
                            (UUID) resultSet.getObject("contract_id")
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
            String sql = "SELECT * FROM tickets WHERE deleted_at IS NULL";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tickets.add(new Ticket(
                        (UUID) resultSet.getObject("id"),
                        Transport.fromString(resultSet.getString("transportType")),
                        resultSet.getBigDecimal("buyingPrice"),
                        resultSet.getBigDecimal("sellingPrice"),
                        resultSet.getDate("sellingDate"),
                        TicketStatus.fromString(resultSet.getString("ticketStatus")),
                        (UUID) resultSet.getObject("contract_id")
                ));
            }
        }
        return tickets;
    }

    public void save(Ticket ticket) throws SQLException {
        String sql = "INSERT INTO tickets (id, transportType, buyingPrice, sellingPrice, sellingDate, ticketStatus, contract_id) " +
                "VALUES (?, CAST(? AS transport), ?, ?, ?, CAST(? AS ticketStatus), ?)";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, ticket.getId());
            statement.setObject(2, ticket.getTransportType().name(), Types.OTHER);
            statement.setBigDecimal(3, ticket.getBuyingPrice());
            statement.setBigDecimal(4, ticket.getSellingPrice());
            statement.setDate(5, new java.sql.Date(ticket.getSellingDate().getTime()));
            statement.setObject(6, ticket.getTicketStatus().name(), Types.OTHER);
            statement.setObject(7, ticket.getContract_id());

            statement.executeUpdate();
        }
    }

    public void update(Ticket ticket) throws SQLException {
        String sql = "UPDATE tickets SET transportType = ?, buyingPrice = ?, sellingPrice = ?, sellingDate = ?, " +
                "ticketStatus = ?, contract_id = ? WHERE id = ?";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, ticket.getTransportType().name(), Types.OTHER);
            statement.setBigDecimal(2, ticket.getBuyingPrice());
            statement.setBigDecimal(3, ticket.getSellingPrice());
            statement.setDate(4, new java.sql.Date(ticket.getSellingDate().getTime()));
            statement.setObject(5, ticket.getTicketStatus().name(), Types.OTHER);
            statement.setObject(6, ticket.getContract_id());
            statement.setObject(7, ticket.getId());

            statement.executeUpdate();
        }
    }
}
