package org.app.Services.Admin;

import org.app.Models.Entities.Admin;
import org.app.Models.Entities.Contract;
import org.app.Models.Entities.PromotionalOffer;
import org.app.Models.Enums.CurrentStatus;
import org.app.Models.Enums.OfferStatus;
import org.app.Models.Enums.ReductionType;
import org.app.tools.databaseC;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ContractServices {

    public void delete(UUID id) throws SQLException {
        String sql = "UPDATE contract SET deleted_at = ? WHERE id = ?";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, Date.valueOf(LocalDateTime.now().toLocalDate()));
            statement.setObject(2, id);

            statement.executeUpdate();
        }
    }

    public void restore(UUID id) throws SQLException {
        String sql = "UPDATE contract SET deleted_at = ? WHERE id = ?";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setNull(1, Types.DATE);
            statement.setObject(2, id);

            statement.executeUpdate();
        }
    }

    public Contract findById(UUID id) throws SQLException {
        try (Connection connection = databaseC.getInstance().getConnection()) {
            String sql = "SELECT * FROM contract WHERE id = ? AND deleted_at IS NULL";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Retrieve the contract data
                    Contract contract = new Contract(
                            (UUID) resultSet.getObject("id"),
                            resultSet.getDate("initDate"),
                            resultSet.getDate("endDate"),
                            resultSet.getBigDecimal("specialTariff"),
                            resultSet.getString("accordConditions"),
                            resultSet.getBoolean("renewed"),
                            CurrentStatus.fromString(resultSet.getString("currentStatus")),
                            (UUID) resultSet.getObject("partner_id"),
                            new ArrayList<>()
                    );

                    // Now fetch associated promotional offers from the pivot table
                    contract.setPromotionalOffers(getPromotionalOffersForContract(id, connection));

                    return contract;
                } else {
                    return null;
                }
            }
        }
    }

    public List<Contract> getContracts() throws SQLException {
        List<Contract> contracts = new ArrayList<>();
        try (Connection connection = databaseC.getInstance().getConnection()) {
            String sql = "SELECT * FROM contract WHERE deleted_at IS NULL";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                UUID contractId = (UUID) resultSet.getObject("id");

                Contract contract = new Contract(
                        contractId,
                        resultSet.getDate("initDate"),
                        resultSet.getDate("endDate"),
                        resultSet.getBigDecimal("specialTariff"),
                        resultSet.getString("accordConditions"),
                        resultSet.getBoolean("renewed"),
                        CurrentStatus.fromString(resultSet.getString("currentStatus")),
                        (UUID) resultSet.getObject("partner_id"),
                        new ArrayList<>()
                );

                // Fetch associated promotional offers for each contract
                contract.setPromotionalOffers(getPromotionalOffersForContract(contractId, connection));

                contracts.add(contract);
            }
        }
        return contracts;
    }

    public void save(Contract contract) throws SQLException {
        String sqlContract = "INSERT INTO contract (id, initDate, endDate, specialTariff, accordConditions, renewed, currentStatus, partner_id) VALUES (?, ?, ?, ?, ?, ?, CAST(? AS currentStatus), ?)";
        try (Connection connection = databaseC.getInstance().getConnection()) {
            // Save the contract
            try (PreparedStatement statement = connection.prepareStatement(sqlContract)) {
                statement.setObject(1, contract.getId());
                statement.setDate(2, Date.valueOf(contract.getInitDate().toLocalDate()));
                statement.setDate(3, Date.valueOf(contract.getEndDate().toLocalDate()));
                statement.setBigDecimal(4, contract.getSpecialTariff());
                statement.setString(5, contract.getAccordConditions());
                statement.setBoolean(6, contract.isRenewed());
                statement.setObject(7, contract.getCurrentStatus().name());
                statement.setObject(8, contract.getPartner_id());
                statement.executeUpdate();
            }

            // Save the relationships in the pivot table
            savePromotionalOffers(contract, connection);
        }
    }

    public void update(Contract contract) throws SQLException {
        String sql = "UPDATE contract SET initDate = ?, endDate = ?, specialTariff = ?, accordConditions = ?, renewed = ?, currentStatus = ?, partner_id = ? WHERE id = ?";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, Date.valueOf(contract.getInitDate().toLocalDate()));
            statement.setDate(2, Date.valueOf(contract.getEndDate().toLocalDate()));
            statement.setBigDecimal(3, contract.getSpecialTariff());
            statement.setString(4, contract.getAccordConditions());
            statement.setBoolean(5, contract.isRenewed());
            statement.setObject(6, contract.getCurrentStatus().name());
            statement.setObject(7, contract.getPartner_id());
            statement.setObject(8, contract.getId());

            statement.executeUpdate();
        }

        // Clear existing promotional offers and update with new ones
        try (Connection connection = databaseC.getInstance().getConnection()) {
            deletePromotionalOffers(contract.getId(), connection);
            savePromotionalOffers(contract, connection);
        }
    }

    // Helper methods

    private List<PromotionalOffer> getPromotionalOffersForContract(UUID contractId, Connection connection) throws SQLException {
        List<PromotionalOffer> promotionalOffers = new ArrayList<>();
        String sql = "SELECT po.* FROM promotionaloffer po " +
                "JOIN contract_promotionaloffer cpo ON po.id = cpo.promotional_offer_id " +
                "WHERE cpo.contract_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, contractId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    promotionalOffers.add(new PromotionalOffer(
                            (UUID) resultSet.getObject("id"),
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getDate("initDate"),
                            resultSet.getDate("endDate"),
                            ReductionType.valueOf(resultSet.getString("reductionType")),
                            resultSet.getFloat("reductionValue"),
                            resultSet.getString("conditions"),
                            OfferStatus.valueOf(resultSet.getString("offerStatus"))
                    ));
                }
            }
        }
        return promotionalOffers;
    }

    private void savePromotionalOffers(Contract contract, Connection connection) throws SQLException {
        String sql = "INSERT INTO contract_promotionaloffer (contract_id, promotional_offer_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (PromotionalOffer offer : contract.getPromotionalOffers()) {
                statement.setObject(1, contract.getId());
                statement.setObject(2, offer.getId());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private void deletePromotionalOffers(UUID contractId, Connection connection) throws SQLException {
        String sql = "DELETE FROM contract_promotionaloffer WHERE contract_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, contractId);
            statement.executeUpdate();
        }
    }
}
