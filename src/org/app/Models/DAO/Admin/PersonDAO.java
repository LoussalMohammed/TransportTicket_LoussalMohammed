package org.app.Models.DAO.Admin;

import org.app.Models.Enums.Role;
import org.app.Models.Entities.Person;
import org.app.tools.databaseC;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PersonDAO {

    public void delete(int id) throws SQLException {
        String sql = "UPDATE persons SET deleted_at = ? WHERE id = ?";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, Date.valueOf(LocalDateTime.now().toLocalDate()));
            statement.setInt(2, id);

            statement.executeUpdate();
        }
    }

    public void restore(int id) throws SQLException {
        String sql = "UPDATE persons SET deleted_at = ? WHERE id = ?";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, null);
            statement.setInt(2, id);

            statement.executeUpdate();
        }
    }
    public Person findById(int id) throws SQLException {
        try (Connection connection = databaseC.getInstance().getConnection()) {
            String sql = "SELECT * FROM persons WHERE id = ? AND deleted_at IS NULL";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return new Person(
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

    public ArrayList<Person> getAllPersons() throws SQLException {
        ArrayList<Person> Persons = new ArrayList<>(10);
        try (Connection connection = databaseC.getInstance().getConnection()) {
            String sql = "SELECT * FROM persons WHERE deleted_at IS NULL";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                Person person = new Person(
                        resultSet.getInt("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("email"),
                        resultSet.getString("phone"),
                        Role.fromString(resultSet.getString("role")),
                        resultSet.getString("hashedPassword"),
                        resultSet.getTimestamp("created_at").toLocalDateTime()
                );
                Persons.add(person);
            }

        }
        return Persons;
    }



    public void save(Person person) throws SQLException {
        String sql = "INSERT INTO persons (id, firstName, lastName, email, phone, role, hashedPassword, created_at) VALUES (?, ?, ?, ?, ?, CAST(? AS role), ?, ?)";


        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, person.getId());
            statement.setString(2, person.getFirstName());
            statement.setString(3, person.getLastName());
            statement.setString(4, person.getEmail());
            statement.setString(5, person.getPhone());
            statement.setObject(6, person.getRole().name().toLowerCase(), java.sql.Types.OTHER); // Convert to lowercase
            statement.setString(7, person.getHashedPassword());
            statement.setTimestamp(8, Timestamp.valueOf(person.getCreatedAt()));
            System.out.println("SQL: " + sql);
            System.out.println("Parameters: " + person.getId() + ", " + person.getFirstName() + ", " + person.getLastName() + ", " + person.getEmail() + ", " + person.getPhone() + ", " + person.getRole().name().toLowerCase() + ", " + person.getCreatedAt());


            statement.executeUpdate();
        }
    }

    public void update(Person person) throws SQLException {
        String sql = "UPDATE persons SET firstName = ?, lastName = ?, email = ?, phone = ?, role = ?, created_at = ? WHERE id = ? AND deleted_at IS NULL";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setString(3, person.getEmail());
            statement.setString(4, person.getPhone());
            statement.setObject(5, person.getRole().name().toLowerCase(), java.sql.Types.OTHER); // Convert to lowercase
            statement.setTimestamp(6, Timestamp.valueOf(person.getCreatedAt()));
            statement.setInt(7, person.getId());
            System.out.println("SQL: " + sql);
            System.out.println("Parameters: " + person.getId() + ", " + person.getFirstName() + ", " + person.getLastName() + ", " + person.getEmail() + ", " + person.getPhone() + ", " + person.getRole().name().toLowerCase() + ", " + person.getCreatedAt());


            statement.executeUpdate();
        }
    }





}
