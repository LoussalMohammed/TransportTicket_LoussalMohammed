package org.app.Models.DAO.Admin;

import org.app.Models.Entities.Route;
import org.app.Tools.databaseC;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.postgresql.core.Oid.UUID;

public class RouteDAO {

    public void delete(int id) throws SQLException {
        String sql = "UPDATE routes SET deleted_at = ? WHERE id = ? ";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, Date.valueOf(LocalDateTime.now().toLocalDate()));
            statement.setInt(2, id);

            statement.executeUpdate();
        }
    }

    public void restore(int id) throws SQLException {
        String sql = "UPDATE routes SET deleted_at = ? WHERE id = ?";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, null);
            statement.setInt(2, id);

            statement.executeUpdate();
        }
    }
    public Route findById(int id) throws SQLException {
        try (Connection connection = databaseC.getInstance().getConnection()) {
            String sql = "SELECT * FROM routes WHERE id = ? AND deleted_at IS NULL";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return new Route(
                            resultSet.getInt("id"),
                            resultSet.getString("departureCity"),
                            resultSet.getString("destinationCity"),
                            resultSet.getTimestamp("departureDate").toLocalDateTime(),
                            resultSet.getTimestamp("arrivalDate").toLocalDateTime(),
                            resultSet.getBigDecimal("price"),
                            (UUID) resultSet.getObject("partnerId")
                    );
                } else {
                    return null;
                }
            }
        }
    }

    public ArrayList<Route> getAllRoutes() throws SQLException {
        ArrayList<Route> Routes = new ArrayList<>(10);
        try (Connection connection = databaseC.getInstance().getConnection()) {
            String sql = "SELECT * FROM routes WHERE deleted_at IS NULL";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                Route route = new Route(
                        resultSet.getInt("id"),
                        resultSet.getString("departureCity"),
                        resultSet.getString("destinationCity"),
                        resultSet.getTimestamp("departureDate").toLocalDateTime(),
                        resultSet.getTimestamp("arrivalDate").toLocalDateTime(),
                        resultSet.getBigDecimal("price"),
                        (UUID) resultSet.getObject("partnerId")

                );
                Routes.add(route);
            }

        }
        return Routes;
    }



    public void save(Route route) throws SQLException {
        String sql = "INSERT INTO routes (id, departureCity, destinationCity, departureDate, arrivalDate, price, partnerId) VALUES (?, ?, ?, ?, ?, ?, ?)";


        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, route.getId());
            statement.setString(2, route.getDepartureCity());
            statement.setString(3, route.getDestinationCity());
            statement.setTimestamp(4, Timestamp.valueOf(Timestamp.valueOf(route.getDepartureDate()).toLocalDateTime()));
            statement.setTimestamp(6, Timestamp.valueOf(Timestamp.valueOf(route.getArrivalDate()).toLocalDateTime()));
            statement.setBigDecimal(5, route.getPrice());
            statement.setObject(7, route.getPartnerId());


            statement.executeUpdate();
        }
    }

    public void update(Route route) throws SQLException {
        String sql = "UPDATE routes SET departureCity = ?, destinationCity = ?, departureDate = ?, arrivalDate = ?, price = ?, partnerId = ? WHERE id = ? AND deleted_at IS NULL";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, route.getDepartureCity());
            statement.setString(2, route.getDestinationCity());
            statement.setTimestamp(3, Timestamp.valueOf(Timestamp.valueOf(route.getDepartureDate()).toLocalDateTime()));
            statement.setTimestamp(4, Timestamp.valueOf(Timestamp.valueOf(route.getArrivalDate()).toLocalDateTime()));
            statement.setBigDecimal(5, route.getPrice());
            statement.setObject(6, route.getPartnerId()); // Convert to lowercase
            statement.setInt(7, route.getId());

            statement.executeUpdate();
        }
    }
    public static Integer getLastId() {
        String sql = "SELECT MAX(id) AS id FROM routes";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            try {
                if(resultSet.next()) {
                    Integer id = resultSet.getInt("id");
                    return id;
                } else {
                    return null;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
