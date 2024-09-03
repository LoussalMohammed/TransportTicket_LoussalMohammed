package org.app.Services.Admin;

import org.app.Models.Entities.Contract;
import org.app.Models.Entities.Partner;
import org.app.Models.Enums.CurrentStatus;
import org.app.Models.Enums.PartenaryStatus;
import org.app.Models.Enums.TransportType;
import org.app.tools.databaseC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PartenerServices {
    public String delete(UUID id) throws SQLException {
        String sql = "DELETE FROM partner WHERE id = ?";
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
    public Partner findById(UUID id) throws SQLException {
        try (Connection connection = databaseC.getInstance().getConnection()) {
            String sql = "SELECT * FROM partner WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return new Partner(
                            (UUID) resultSet.getObject("id"),
                            resultSet.getString("companyName"),
                            resultSet.getString("commercialContact"),
                            TransportType.fromString(resultSet.getString("transportType")),
                            resultSet.getString("geographicZone"),
                            resultSet.getString("specialCondition"),
                            PartenaryStatus.fromString((resultSet.getString("partenaryStatus"))),
                            resultSet.getDate("createdAt")
                    );
                } else {
                    return null;
                }
            }
        }
    }

    public List<Partner> getPartners() throws SQLException {
        List<Partner> partners = new ArrayList<>();
        try(Connection connection = databaseC.getInstance().getConnection()) {
            String sql = "SELECT * FROM partner";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                partners.add(new Partner(
                        (UUID) resultSet.getObject("id"),
                        resultSet.getString("companyName"),
                        resultSet.getString("commercialContact"),
                        TransportType.fromString(resultSet.getString("transportType")),
                        resultSet.getString("geographicZone"),
                        resultSet.getString("specialCondition"),
                        PartenaryStatus.fromString(resultSet.getString("partenaryStatus")), // Adjust based on actual contract columns
                        resultSet.getDate("createdAt")
                ));

            }

        }
        return partners;
    }


    public void save(Partner partner) throws SQLException {
        String sql = "INSERT INTO partner (id, companyName, commercialContact, transportType, geographicZone, specialCondition, partenaryStatus, createdAt) VALUES (?, ?, ?, CAST(? AS transportType), ?, ?, CAST(? AS currentStatus), ?)";
        try(Connection connection = databaseC.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, partner.getId());
            statement.setString(2, partner.getCompanyName());
            statement.setString(3, partner.getCommercialContact());
            statement.setObject(4, partner.getTransportType().name(), Types.OTHER);
            statement.setString(5, partner.getGeographicZone());
            statement.setString(6, partner.getSpecialCondition());
            statement.setObject(4, partner.getPartenaryStatus().name(), Types.OTHER);
            statement.setDate(7, partner.getCreatedAt());

            statement.executeUpdate();
        }

    }

    public void update(Partner partner) throws SQLException {
        String sql = "UPDATE partner SET id = ?, companyName = ?, commercialContact = ?, transportType = ?, geographicZone = ?, specialCondition = ?, partenaryStatus = ?, createdAt = ? WHERE id = ?";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Set the parameters for the PreparedStatement
            statement.setObject(1, partner.getId());
            statement.setString(2, partner.getCompanyName());
            statement.setString(2, partner.getCommercialContact());
            statement.setObject(6, partner.getTransportType().name(), java.sql.Types.OTHER);
            statement.setString(2, partner.getGeographicZone());
            statement.setString(2, partner.getSpecialCondition());
            statement.setObject(6, partner.getPartenaryStatus().name(), java.sql.Types.OTHER);
            statement.setDate(7, partner.getCreatedAt()); // Set the ID as the last parameter for the WHERE clause

            // Execute the update
            statement.executeUpdate();
        }
    }
}
