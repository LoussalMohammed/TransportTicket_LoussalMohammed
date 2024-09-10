package org.app.Models.Entities;

import org.app.Models.DAO.Admin.AdminDAO;
import org.app.Models.DAO.Admin.PersonDAO;
import org.app.Models.Enums.Role;
import org.app.Models.Helpers.PasswordUtil;
import org.views.admin.person.AdminView;
import org.views.admin.person.PersonView;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Person {
    private static PersonView personView = new PersonView();
    private static PersonDAO personDAO = new PersonDAO();
    private int id; // auto-incrementing ID
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Role role;

    private String hashedPassword;
    private LocalDateTime createdAt;
    private Date deleted_at;

    public Person(int id, String firstName, String lastName, String email, String phone, Role role, String password, LocalDateTime createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.hashedPassword = PasswordUtil.hashPassword(password);
        this.createdAt = createdAt;
    }

    // Getters and setters for each field
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
    public String getHashedPassword() {
        return this.hashedPassword;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Date getDeletedAt() {
        return deleted_at;
    }

    public void setDeletedAt(Date deleted_at) {
        this.deleted_at = deleted_at;
    }


    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                ", createdAt=" + createdAt +
                '}';
    }

    public static void getPersonById() throws SQLException {
        int id = personView.getPerson();
        Person person = personDAO.findById(id);
        personView.displayPerson(person);
    }

    public static void getAllPersons() throws SQLException {
        ArrayList<Person> persons = personDAO.getAllPersons();
        personView.displayPersonsList(persons);
    }

    public static void addPerson() throws SQLException{
        Person newPerson = personView.addPerson();
        personDAO.save(newPerson);
    }

    public static void updatePerson() throws SQLException {
        Person updatePerson = personView.addPerson();
        personDAO.update(updatePerson);
    }

    public static void deletePerson() throws SQLException {
        int deletedPersonId = personView.getPerson();
        personDAO.delete(deletedPersonId);
    }

    public static void restorePerson() throws SQLException {
        int restoredPersonId = personView.getPerson();
        personDAO.restore(restoredPersonId);
    }
}
