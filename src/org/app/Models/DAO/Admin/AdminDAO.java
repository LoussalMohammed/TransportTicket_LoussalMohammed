package org.app.Models.DAO.Admin;

import org.app.Models.Entities.Admin;
import org.app.Models.Entities.Contract;
import org.app.Models.Enums.CurrentStatus;
import org.app.Models.Enums.Role;
import org.app.tools.databaseC;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdminDAO {

    public Admin findById(int id) throws SQLException {
        try (Connection connection = databaseC.getInstance().getConnection()) {
            String sql = "SELECT * FROM administrators WHERE id = ? AND deleted_at IS NULL";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return new Admin(
                            resultSet.getInt("id"),
                            resultSet.getString("firstName"),
                            resultSet.getString("lastName"),
                            resultSet.getString("email"),
                            resultSet.getString("phone"),
                            Role.fromString(resultSet.getString("role")),
                            resultSet.getString("hashedPassword"),
                            resultSet.getTimestamp("created_at").toLocalDateTime()
                    );
                } else {
                    return null;
                }
            }
        }
    }

    public ArrayList<Admin> getAllAdmin() throws SQLException {
        ArrayList<Admin> admins = new ArrayList<>();
        try (Connection connection = databaseC.getInstance().getConnection()) {
            String sql = "SELECT * FROM administrators WHERE deleted_at IS NULL";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Admin admin = new Admin(
                        resultSet.getInt("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("email"),
                        resultSet.getString("phone"),
                        Role.fromString(resultSet.getString("role")),
                        resultSet.getString("hashedPassword"),
                        resultSet.getTimestamp("created_at").toLocalDateTime()
                );
                admins.add(admin);
            }
        }
        return admins;
    }

    public void save(Admin admin) throws SQLException {
        String sql = "INSERT INTO administrators (id, firstName, lastName, email, phone, role, hashedPassword, created_at) VALUES (?, ?, ?, ?, ?, CAST(? AS role), ?, ?)";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, admin.getId());
            statement.setString(2, admin.getFirstName());
            statement.setString(3, admin.getLastName());
            statement.setString(4, admin.getEmail());
            statement.setString(5, admin.getPhone());
            statement.setObject(6, admin.getRole().name().toLowerCase(), java.sql.Types.OTHER); // Convert to lowercase
            statement.setString(7, admin.getHashedPassword());
            statement.setTimestamp(8, Timestamp.valueOf(admin.getCreatedAt()));

            statement.executeUpdate();
        }
    }

    public void update(Admin admin) throws SQLException {
        String sql = "UPDATE administrators SET firstName = ?, lastName = ?, email = ?, phone = ?, role = ?, created_at = ? WHERE id = ?";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, admin.getFirstName());
            statement.setString(2, admin.getLastName());
            statement.setString(3, admin.getEmail());
            statement.setString(4, admin.getPhone());
            statement.setObject(5, admin.getRole().name().toLowerCase(), java.sql.Types.OTHER); // Convert to lowercase
            statement.setTimestamp(6, Timestamp.valueOf(admin.getCreatedAt()));
            statement.setInt(7, admin.getId());

            statement.executeUpdate();
        }
    }
    public void delete(int id) throws SQLException {
        String sql = "UPDATE administrators SET deleted_at = ? WHERE id = ?";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, Date.valueOf(LocalDateTime.now().toLocalDate()));
            statement.setInt(2, id);

            statement.executeUpdate();
        }
    }

    public void restore(int id) throws SQLException {
        String sql = "UPDATE administrators SET deleted_at = ? WHERE id = ?";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, null);
            statement.setInt(2, id);

            statement.executeUpdate();
        }
    }
}