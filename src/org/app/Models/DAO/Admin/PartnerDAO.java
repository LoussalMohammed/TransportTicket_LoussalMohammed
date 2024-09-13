package org.app.Models.DAO.Admin;

import org.app.Models.Entities.Contract;
import org.app.Models.Entities.Partner;
import org.app.Models.Enums.CurrentStatus;
import org.app.Models.Enums.PartenaryStatus;
import org.app.Models.Enums.Transport;
import org.app.Tools.databaseC;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PartnerDAO {
    public void delete(UUID id) throws SQLException {
        String sql = "UPDATE partners SET deleted_at = ? WHERE id = ?";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, Date.valueOf(LocalDateTime.now().toLocalDate()));
            statement.setObject(2, id);

            statement.executeUpdate();
        }
    }

    public void restore(UUID id) throws SQLException {
        String sql = "UPDATE partners SET deleted_at = ? WHERE id = ?";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, null);
            statement.setObject(2, id);

            statement.executeUpdate();
        }
    }

    public Partner findById(UUID id) throws SQLException {
        try (Connection connection = databaseC.getInstance().getConnection()) {
            String sql = "SELECT * FROM partners WHERE id = ? AND deleted_at IS NULL";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Partner partner = new Partner(
                            (UUID) resultSet.getObject("id"),
                            resultSet.getString("companyName"),
                            resultSet.getString("commercialContact"),
                            Transport.fromString(resultSet.getString("transportType")),
                            resultSet.getString("geographicZone"),
                            resultSet.getString("specialCondition"),
                            PartenaryStatus.fromString(resultSet.getString("partnerStatus")),
                            // Retrieve created_at as Timestamp and convert to LocalDateTime
                            resultSet.getTimestamp("created_at").toLocalDateTime(),
                            new ArrayList<>()
                    );

                    // Fetch and add contracts for the partner
                    List<Contract> contracts = getContractsByPartnerId(partner.getId());
                    partner.getContracts().addAll(contracts);

                    return partner;
                } else {
                    return null;
                }
            }
        }
    }

    public List<Partner> getPartners() throws SQLException {
        List<Partner> partners = new ArrayList<>();
        try (Connection connection = databaseC.getInstance().getConnection()) {
            String sql = "SELECT * FROM partners WHERE deleted_at IS NULL";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Partner partner = new Partner(
                        (UUID) resultSet.getObject("id"),
                        resultSet.getString("companyName"),
                        resultSet.getString("commercialContact"),
                        Transport.fromString(resultSet.getString("transportType")),
                        resultSet.getString("geographicZone"),
                        resultSet.getString("specialCondition"),
                        PartenaryStatus.fromString(resultSet.getString("partnerStatus")),
                        resultSet.getTimestamp("created_at").toLocalDateTime(),
                        new ArrayList<>()
                );

                // Fetch and add contracts for each partner
                List<Contract> contracts = getContractsByPartnerId(partner.getId());
                partner.getContracts().addAll(contracts);

                partners.add(partner);
            }
        }
        return partners;
    }

    private List<Contract> getContractsByPartnerId(UUID partnerId) throws SQLException {
        List<Contract> contracts = new ArrayList<>();
        String sql = "SELECT * FROM contracts WHERE partner_id = ?";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, partnerId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                contracts.add(new Contract(
                        (UUID) resultSet.getObject("id"),
                        resultSet.getDate("initDate"),
                        resultSet.getDate("endDate"),
                        resultSet.getBigDecimal("specialTariff"),
                        resultSet.getString("accordConditions"),
                        resultSet.getBoolean("renewed"),
                        CurrentStatus.fromString(resultSet.getString("currentStatus")),
                        (UUID) resultSet.getObject("partner_id"),
                        new ArrayList<>()
                ));
            }
        }
        return contracts;
    }

    public void save(Partner partner) throws SQLException {
        // SQL query to insert a new partner record
        String sql = "INSERT INTO partners (id, companyName, commercialContact, transportType, geographicZone, specialCondition, partnerStatus, created_at) VALUES (?, ?, ?, CAST(? AS transport), ?, ?, CAST(? AS partnerStatus), ?)";

        // Using try-with-resources to ensure connection and statement are closed automatically
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Setting the parameters for the prepared statement
            statement.setObject(1, partner.getId()); // Assuming partner.getId() returns a valid UUID
            statement.setString(2, partner.getCompanyName());
            statement.setString(3, partner.getCommercialContact());
            statement.setString(4, partner.getTransport().name()); // Enum name as string
            statement.setString(5, partner.getGeographicZone());
            statement.setString(6, partner.getSpecialCondition());
            statement.setString(7, partner.getPartenaryStatus().name()); // Enum name as string

            // Convert LocalDateTime to Timestamp
            LocalDateTime createdAt = partner.getCreatedAt();
            if (createdAt != null) {
                // Convert LocalDateTime to Timestamp
                statement.setTimestamp(8, Timestamp.valueOf(createdAt));
            } else {
                // Handle null case, set to current timestamp or throw an exception as per your logic
                statement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            }

            // Execute the update
            statement.executeUpdate();

            // Save contracts associated with this partner
            for (Contract contract : partner.getContracts()) {
                saveContract(contract);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception for debugging
            throw e; // Re-throw the exception if needed
        }
    }




    public void saveContract(Contract contract) throws SQLException {
        String sql = "INSERT INTO contracts (id, initDate, endDate, specialTariff, accordConditions, renewed, currentStatus, partner_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, contract.getId());
            statement.setDate(2, contract.getInitDate());
            statement.setDate(3, contract.getEndDate());
            statement.setBigDecimal(4, contract.getSpecialTariff());
            statement.setString(5, contract.getAccordConditions());
            statement.setBoolean(6, contract.isRenewed());
            statement.setObject(7, contract.getCurrentStatus().name(), Types.OTHER);
            statement.setObject(8, contract.getPartner_id());

            statement.executeUpdate();
        }
    }


    public void update(Partner partner) throws SQLException {
        String sql = "UPDATE partners SET companyName = ?, commercialContact = ?, transportType = CAST(? AS transport), " +
                "geographicZone = ?, specialCondition = ?, partnerstatus = CAST(? AS partnerStatus) " +
                "WHERE id = ?";

        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, partner.getCompanyName());
            statement.setString(2, partner.getCommercialContact());
            statement.setObject(3, partner.getTransport().name(), Types.OTHER);
            statement.setString(4, partner.getGeographicZone());
            statement.setString(5, partner.getSpecialCondition());
            statement.setObject(6, partner.getPartenaryStatus().name(), Types.OTHER);
            statement.setObject(7, partner.getId());

            statement.executeUpdate();

            // Optionally update contracts if needed
            for (Contract contract : partner.getContracts()) {
                // Assume you have an updateContract method
                updateContract(contract);
            }
        }
    }

    private void updateContract(Contract contract) throws SQLException {
        String sql = "UPDATE contracts SET initDate = ?, endDate = ?, specialTariff = ?, " +
                "accordConditions = ?, renewed = ?, currentStatus = CAST(? AS currentStatus) " +
                "WHERE id = ?";

        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, contract.getInitDate());
            statement.setDate(2, contract.getEndDate());
            statement.setBigDecimal(3, contract.getSpecialTariff());
            statement.setString(4, contract.getAccordConditions());
            statement.setBoolean(5, contract.isRenewed());
            statement.setObject(6, contract.getCurrentStatus().name(), Types.OTHER);
            statement.setObject(7, contract.getId());

            statement.executeUpdate();
        }
    }

}
