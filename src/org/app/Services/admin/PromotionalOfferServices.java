package org.app.Services.Admin;

import org.app.Models.Entities.PromotionalOffer;
import org.app.Models.Enums.ReductionType;
import org.app.Models.Enums.OfferStatus;
import org.app.tools.databaseC;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PromotionalOfferServices {

    // Method to delete a promotional offer by ID
    public void delete(UUID id) throws SQLException {
        String sql = "UPDATE promotionalOffer SET deleted_at = ? WHERE id = ?";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, Date.valueOf(LocalDateTime.now().toLocalDate()));
            statement.setObject(2, id);

            statement.executeUpdate();
        }
    }

    public void restore(UUID id) throws SQLException {
        String sql = "UPDATE promotionalOffer SET deleted_at = ? WHERE id = ?";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, null);
            statement.setObject(2, id);

            statement.executeUpdate();
        }
    }

    // Method to find a promotional offer by ID
    public static PromotionalOffer findById(UUID id) throws SQLException {
        try (Connection connection = databaseC.getInstance().getConnection()) {
            String sql = "SELECT * FROM promotionaloffer WHERE id = ? AND deleted_at IS NULL";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new PromotionalOffer(
                            (UUID) resultSet.getObject("id"),
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getDate("initDate"),
                            resultSet.getDate("endDate"),
                            ReductionType.valueOf(resultSet.getString("reductionType")),
                            resultSet.getFloat("reductionValue"),
                            resultSet.getString("conditions"),
                            OfferStatus.valueOf(resultSet.getString("offerStatus"))
                    );
                } else {
                    return null;
                }
            }
        }
    }

    // Method to get all promotional offers
    public List<PromotionalOffer> getPromotionalOffers() throws SQLException {
        List<PromotionalOffer> offers = new ArrayList<>();
        try (Connection connection = databaseC.getInstance().getConnection()) {
            String sql = "SELECT * FROM promotionaloffer WHERE deleted_at IS NULL";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                offers.add(new PromotionalOffer(
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
        return offers;
    }

    // Method to save a new promotional offer
    public void save(PromotionalOffer offer) throws SQLException {
        String sql = "INSERT INTO promotionaloffer (id, name, description, initDate, endDate, reductionType, reductionValue, conditions, offerStatus) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, offer.getId());
            statement.setString(2, offer.getName());
            statement.setString(3, offer.getDescription());
            statement.setDate(4, new java.sql.Date(offer.getInitDate().getTime()));
            statement.setDate(5, new java.sql.Date(offer.getEndDate().getTime()));
            statement.setObject(6, offer.getReductionType().name(), java.sql.Types.OTHER);
            statement.setFloat(7, offer.getReductionValue());
            statement.setString(8, offer.getCondition());
            statement.setObject(9, offer.getOfferStatus().name(), java.sql.Types.OTHER);

            statement.executeUpdate();
        }
    }

    // Method to update an existing promotional offer
    public void update(PromotionalOffer offer) throws SQLException {
        String sql = "UPDATE promotionaloffer SET name = ?, description = ?, initDate = ?, endDate = ?, reductionType = ?, reductionValue = ?, conditions = ?, offerStatus = ? WHERE id = ?";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, offer.getName());
            statement.setString(2, offer.getDescription());
            statement.setDate(3, new java.sql.Date(offer.getInitDate().getTime()));
            statement.setDate(4, new java.sql.Date(offer.getEndDate().getTime()));
            statement.setObject(5, offer.getReductionType().name(), java.sql.Types.OTHER);
            statement.setFloat(6, offer.getReductionValue());
            statement.setString(7, offer.getCondition());
            statement.setObject(8, offer.getOfferStatus().name(), java.sql.Types.OTHER);
            statement.setObject(9, offer.getId());

            statement.executeUpdate();
        }
    }
}
