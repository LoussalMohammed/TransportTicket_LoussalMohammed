package org.app.Models.Checkers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.app.Tools.databaseC;

public class ContractChecker {

    public static void main(String[] args) {

        contractMonitor();
    }

    public static void contractMonitor() {
        String selectSql = "SELECT id, initDate, endDate, renewed " +
                "FROM contracts " +
                "WHERE currentStatus != 'ENDED'";

        String updateSql = "UPDATE contracts " +
                "SET endDate = ?, currentStatus = ?::currentStatus " +
                "WHERE id = ?";

        try(Connection connection = databaseC.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(selectSql);
            ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                UUID contractId = (UUID) resultSet.getObject("id");
                LocalDateTime initDate = resultSet.getTimestamp("initDate").toLocalDateTime();
                LocalDateTime endDate = resultSet.getTimestamp("endDate").toLocalDateTime();
                boolean renewed = resultSet.getBoolean("renewed");

                if (LocalDateTime.now().isAfter(endDate) || LocalDateTime.now().equals(endDate)) {
                    try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
                        if (renewed) {
                            LocalDateTime newEndDate = endDate.plusMonths(6);
                            preparedStatement.setTimestamp(1, java.sql.Timestamp.valueOf(newEndDate));
                            preparedStatement.setString(2, "IN_PROGRESS");
                        } else {
                            preparedStatement.setTimestamp(1, java.sql.Timestamp.valueOf(endDate));
                            preparedStatement.setString(2, "ENDED");
                        }

                        preparedStatement.setObject(3, contractId);
                        preparedStatement.executeUpdate();
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
