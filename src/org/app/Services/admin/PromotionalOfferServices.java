package org.app.Services.Admin;

import org.app.Models.Entities.PromotionalOffer;
import org.app.Models.Enums.ReductionType;
import org.app.Models.Enums.OfferStatus;
import org.app.tools.databaseC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PromotionalOfferServices {

    // Method to delete a promotional offer by ID
    public String delete(UUID id) throws SQLException {
        String sql = "DELETE FROM promotional_offer WHERE id = ?";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return "Promotional Offer Deleted Successfully!";
            } else {
                return "Promotional Offer Deletion Failed!";
            }
        }
    }

    // Method to find a promotional offer by ID
    public PromotionalOffer findById(UUID id) throws SQLException {
        try (Connection connection = databaseC.getInstance().getConnection()) {
            String sql = "SELECT * FROM promotional_offer WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new PromotionalOffer(
                            (UUID) resultSet.getObject("id"),
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getDate("init_date"),
                            resultSet.getDate("end_date"),
                            ReductionType.valueOf(resultSet.getString("reduction_type")),
                            resultSet.getFloat("reduction_value"),
                            resultSet.getString("condition"),
                            OfferStatus.valueOf(resultSet.getString("offer_status"))
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
            String sql = "SELECT * FROM promotional_offer";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                offers.add(new PromotionalOffer(
                        (UUID) resultSet.getObject("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDate("init_date"),
                        resultSet.getDate("end_date"),
                        ReductionType.valueOf(resultSet.getString("reduction_type")),
                        resultSet.getFloat("reduction_value"),
                        resultSet.getString("condition"),
                        OfferStatus.valueOf(resultSet.getString("offer_status"))
                ));
            }
        }
        return offers;
    }

    // Method to save a new promotional offer
    public void save(PromotionalOffer offer) throws SQLException {
        String sql = "INSERT INTO promotional_offer (id, name, description, init_date, end_date, reduction_type, reduction_value, condition, offer_status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, offer.getId());
            statement.setString(2, offer.getName());
            statement.setString(3, offer.getDescription());
            statement.setDate(4, new java.sql.Date(offer.getInitDate().getTime()));
            statement.setDate(5, new java.sql.Date(offer.getEndDate().getTime()));
            statement.setString(6, offer.getReductionType().name());
            statement.setFloat(7, offer.getReductionValue());
            statement.setString(8, offer.getCondition());
            statement.setString(9, offer.getOfferStatus().name());

            statement.executeUpdate();
        }
    }

    // Method to update an existing promotional offer
    public void update(PromotionalOffer offer) throws SQLException {
        String sql = "UPDATE promotional_offer SET name = ?, description = ?, init_date = ?, end_date = ?, reduction_type = ?, reduction_value = ?, condition = ?, offer_status = ? WHERE id = ?";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, offer.getName());
            statement.setString(2, offer.getDescription());
            statement.setDate(3, new java.sql.Date(offer.getInitDate().getTime()));
            statement.setDate(4, new java.sql.Date(offer.getEndDate().getTime()));
            statement.setString(5, offer.getReductionType().name());
            statement.setFloat(6, offer.getReductionValue());
            statement.setString(7, offer.getCondition());
            statement.setString(8, offer.getOfferStatus().name());
            statement.setObject(9, offer.getId());

            statement.executeUpdate();
        }
    }
}
