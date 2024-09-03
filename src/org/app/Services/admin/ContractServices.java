package org.app.Services.Admin;

import org.app.Models.Entities.Admin;
import org.app.Models.Entities.Contract;
import org.app.Models.Enums.CurrentStatus;
import org.app.tools.databaseC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ContractServices {

    public String delete(UUID id) throws SQLException {
        String sql = "DELETE FROM contract WHERE id = ?";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);

            int rowsAffected = statement.executeUpdate(); // Use executeUpdate() for DELETE

            if (rowsAffected > 0) {
                return "Row Deleted Successfully!";
            } else {
                return "Row Haven't Been Deleted!";
            }
        }
    }
    public Contract findById(UUID id) throws SQLException {
        try (Connection connection = databaseC.getInstance().getConnection()) {
            String sql = "SELECT * FROM contract WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return new Contract (
                            (UUID) resultSet.getObject("id"),
                            resultSet.getDate("initDate"),
                            resultSet.getDate("endDate"),
                            resultSet.getBigDecimal("specialTariff"),
                            resultSet.getString("accordConditions"),
                            resultSet.getBoolean("renewed"),
                            CurrentStatus.fromString(resultSet.getString("currentStatus"))
                    );
                } else {
                    return null;
                }
            }
        }
    }

    public List<Contract> getContracts() throws SQLException {
        List<Contract> contracts = new ArrayList<>();
        try(Connection connection = databaseC.getInstance().getConnection()) {
            String sql = "SELECT * FROM contract";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                contracts.add(new Contract(
                        (UUID) resultSet.getObject("id"),
                        resultSet.getDate("initDate"),
                        resultSet.getDate("endDate"),
                        resultSet.getBigDecimal("specialTariff"),
                        resultSet.getString("accordConditions"),
                        resultSet.getBoolean("renewed"), // Adjust based on actual contract columns
                        CurrentStatus.fromString(resultSet.getString("currentStatus"))
                ));

            }

        }
        return contracts;
    }


    public void save(Contract contract) throws SQLException {
        String sql = "INSERT INTO contract (id, initDate, endDate, specialTariff, accordConditions, renewed, currentStatus) VALUES (?, ?, ?, ?, ?, ?, CAST(? AS currentStatus))";
        try(Connection connection = databaseC.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, contract.getId());
            statement.setDate(2, Date.valueOf(contract.getInitDate().toLocalDate()));
            statement.setDate(3, Date.valueOf(contract.getEndDate().toLocalDate()));
            statement.setBigDecimal(4, contract.getSpecialTariff());
            statement.setString(5, contract.getAccordConditions());
            statement.setBoolean(6, contract.isRenewed());
            statement.setObject(7, contract.getCurrentStatus().name());

            statement.executeUpdate();
        }

    }

    public void update(Contract contract) throws SQLException {
        String sql = "UPDATE contract SET initDate = ?, endDate = ?, specialTariff = ?, accordConditions = ?, renewed = ?, currentStatus = ? WHERE id = ?";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Set the parameters for the PreparedStatement
            statement.setDate(1, contract.getInitDate());
            statement.setDate(2, contract.getEndDate());
            statement.setBigDecimal(3, contract.getSpecialTariff());
            statement.setString(4, contract.getAccordConditions());
            statement.setBoolean(5, contract.isRenewed());
            statement.setObject(6, contract.getCurrentStatus().name(), java.sql.Types.OTHER);
            statement.setObject(7, contract.getId()); // Set the ID as the last parameter for the WHERE clause

            // Execute the update
            statement.executeUpdate();
        }
    }



}
