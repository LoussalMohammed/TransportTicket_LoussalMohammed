package org.app.Models.Entities;

import org.app.Models.Enums.Role;

import java.time.LocalDateTime;
import java.util.List;

public class Admin extends Person {

    public Admin(int id, String firstName, String lastName, String email, String phone, Role role, String hashedPassword, LocalDateTime createdAt) {
        super(id, firstName, lastName, email, phone, role, hashedPassword,createdAt);
    }



    @Override
    public String toString() {
        return "Admin{" +
                ", Person=" + super.toString() +
                '}';
    }
}
