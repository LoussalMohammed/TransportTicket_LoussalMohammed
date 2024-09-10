package org.views.admin.person;

import org.app.Models.Entities.Admin;
import org.app.Models.Enums.Role;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Scanner;
import java.util.UUID;

import org.app.Models.Helpers.InputValidator;
import org.app.Models.Helpers.InputValidator.*;

public class AdminView {

    private Scanner scanner = new Scanner(System.in);

    public int operationType() {
        System.out.println("\n==========================================================================================================================");
        System.out.println("                                             Welcome to the Admin Entity Management                                         ");
        System.out.println("============================================================================================================================");

        System.out.println("\n Choose Type Of Operation: ListOne => 1, ListAll => 2, Add => 3, Update => 4, Delete => 5, Restore => 6, To Get Out => 0:");
        Integer operation = scanner.nextInt();
        scanner.nextLine();
        return operation;
    }

    public int getAdmin() {
        System.out.println("\n Enter the ID of the Admin:");
        int adminID = scanner.nextInt();
        scanner.nextLine();
        return adminID;
    }

    public void displayAdmin(Admin admin) {
        System.out.println("\n==================================================================================================================================================================================");
        System.out.println("|                          |                                  |                              |                              |                              |");
        System.out.println("|            id            |               name               |            email             |          phone Number        |             role             |");
        System.out.println("|                          |                                  |                              |                              |                              |");
        System.out.println("\n==================================================================================================================================================================================");

        System.out.println("\n======================================================================================================================================================================");
        System.out.println("|                          |                                  |                              |                              |                              |");
        System.out.println("|    "+admin.getId()+"     |    "+admin.getFirstName()+"      |     "+admin.getEmail()+"     |    "+admin.getPhone()+"      |     "+admin.getRole()+"      |");
        System.out.println("|                          |                                  |                              |                              |                              |");
        System.out.println("\n======================================================================================================================================================================");

    }

    public void displayAdminsList(ArrayList<Admin> admins) {
        System.out.println("\n======================================================================================================================================================================================");
        System.out.println("|                                   |                                    |                                     |                                    |                                   |");
        System.out.println("|                 id                |             name                   |                email                |               Phone                |               Role                |");
        System.out.println("|                                   |                                    |                                     |                                    |                                   |");
        System.out.println("\n======================================================================================================================================================================================");
        for(int i = 0; i < admins.size(); i++) {
            Admin admin = admins.get(i);
            System.out.println("\n======================================================================================================================================================================================");
            System.out.println("|                                   |                                    |                                  |                                   |                                   |");
            System.out.println("|      "+admin.getId()+"            |      "+admin.getFirstName()+"      |      "+admin.getEmail()+"        |       "+admin.getPhone()+"        |        "+admin.getRole()+"        |");
            System.out.println("|                                   |                                    |                                  |                                   |                                   |");
            System.out.println("\n======================================================================================================================================================================================");
        }
    }

    public Admin addAdmin() {
        System.out.println("\n================================================================================");
        System.out.println("Enter Admin id:\t");
        int adminId = scanner.nextInt();
        scanner.nextLine();
        String adminFirstName = null;
        do {
            System.out.println("Enter Admin First Name:\t");
            adminFirstName = scanner.nextLine();
        } while(adminFirstName.length() < 2 | !InputValidator.validateName(adminFirstName));

        String adminLastName = null;
        do {
            System.out.println("Enter Admin Last Name:\t");
            adminLastName = scanner.nextLine();
        } while(adminLastName.length() < 2 | !InputValidator.validateName(adminLastName));
        String adminEmail = null;
        do {
            System.out.println("Enter Admin Email:\t");
            adminEmail = scanner.nextLine();
        } while(!InputValidator.validateEmail(adminEmail));
        String adminPhone = null;
        do {
            System.out.println("Enter Admin Phone Number:\t");
            adminPhone = scanner.nextLine();
        } while(!InputValidator.validatePhoneNumber(adminPhone));

        Role adminRole = Role.ADMIN;
        LocalDateTime adminCreated_At = LocalDateTime.now();

        String adminPassword;
        System.out.println("Enter Admin Password :\t");
        adminPassword = scanner.nextLine();
        while(!InputValidator.validatePassword(adminPassword)) {
            System.out.println("Password Format Is Invalid!! You Use Uppercase Lowercase Chars, And Digits.");
            System.out.println("Enter Admin Password :\t");
            System.out.println(adminPassword);
            adminPassword = scanner.nextLine();
        }

        Admin admin = new Admin(adminId, adminFirstName, adminLastName, adminEmail, adminPhone, adminRole, adminPassword, adminCreated_At);
        System.out.println("\n================================================================================");
            return admin;

    }

}
