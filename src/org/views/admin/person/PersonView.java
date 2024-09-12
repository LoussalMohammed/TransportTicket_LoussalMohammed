package org.views.admin.person;

import org.app.Models.Entities.Person;
import org.app.Models.Enums.Role;
import org.app.Models.Helpers.InputValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class PersonView {
    private Scanner scanner = new Scanner(System.in);

    public int operationType() {
        System.out.println("\n==========================================================================================================================");
        System.out.println("                                             Welcome to the Person Entity Management                                         ");
        System.out.println("============================================================================================================================");

        System.out.println("\n Choose Type Of Operation: ListOne => 1, ListAll => 2, Add => 3, Update => 4, Delete => 5, Restore => 6, To Get Out => 0:");
        Integer operation = scanner.nextInt();
        scanner.nextLine();
        return operation;
    }

    public int getPerson() {
        System.out.println("\n Enter the ID of the Person:");
        int personID = scanner.nextInt();
        scanner.nextLine();
        return personID;
    }

    public void displayPerson(Person person) {
        System.out.println("\n==================================================================================================================================================================================");
        System.out.println("|                          |                                  |                              |                              |                              |");
        System.out.println("|            id            |               name               |            email             |          phone Number        |             role             |");
        System.out.println("|                          |                                  |                              |                              |                              |");
        System.out.println("\n==================================================================================================================================================================================");

        System.out.println("\n======================================================================================================================================================================");
        System.out.println("|                          |                                  |                              |                              |                              |");
        System.out.println("|    "+person.getId()+"     |    "+person.getFirstName()+"      |     "+person.getEmail()+"     |    "+person.getPhone()+"      |     "+person.getRole()+"      |");
        System.out.println("|                          |                                  |                              |                              |                              |");
        System.out.println("\n======================================================================================================================================================================");

    }

    public void displayPersonsList(ArrayList<Person> persons) {
        System.out.println("\n======================================================================================================================================================================================");
        System.out.println("|                                   |                                    |                                     |                                    |                                   |");
        System.out.println("|                 id                |             name                   |                email                |               Phone                |               Role                |");
        System.out.println("|                                   |                                    |                                     |                                    |                                   |");
        System.out.println("\n======================================================================================================================================================================================");
        persons.stream()
                .forEach(person -> {
                    System.out.println("\n======================================================================================================================================================================================");
                    System.out.println("|                                   |                                    |                                  |                                   |                                   |");
                    System.out.println("|      "+person.getId()+"            |      "+person.getFirstName()+"      |      "+person.getEmail()+"        |       "+person.getPhone()+"        |        "+person.getRole()+"        |");
                    System.out.println("|                                   |                                    |                                  |                                   |                                   |");
                    System.out.println("\n======================================================================================================================================================================================");
                });
    }

    public Person addPerson() {
        System.out.println("\n================================================================================");
        System.out.println("Enter Person id:\t");
        int personId = scanner.nextInt();
        scanner.nextLine();
        String personFirstName = null;
        do {
            System.out.println("Enter Person First Name:\t");
            personFirstName = scanner.nextLine();
        } while(personFirstName.length() < 2 | !InputValidator.validateName(personFirstName));

        String personLastName = null;
        do {
            System.out.println("Enter Person Last Name:\t");
            personLastName = scanner.nextLine();
        } while(personLastName.length() < 2 | !InputValidator.validateName(personLastName));
        String personEmail = null;
        do {
            System.out.println("Enter Person Email:\t");
            personEmail = scanner.nextLine();
        } while(!InputValidator.validateEmail(personEmail));
        String personPhone = null;
        do {
            System.out.println("Enter Person Phone Number:\t");
            personPhone = scanner.nextLine();
        } while(!InputValidator.validatePhoneNumber(personPhone));

        Role personRole = Role.USER;
        LocalDateTime personCreated_At = LocalDateTime.now();

        String personPassword;
        System.out.println("Enter Person Password :\t");
        personPassword = scanner.nextLine();
        while(!InputValidator.validatePassword(personPassword)) {
            System.out.println("Password Format Is Invalid!! You Use Uppercase Lowercase Chars, And Digits.");
            System.out.println("Enter Person Password :\t");
            System.out.println(personPassword);
            personPassword = scanner.nextLine();
        }

        Person person = new Person(personId, personFirstName, personLastName, personEmail, personPhone, personRole, personPassword, personCreated_At);
        System.out.println("\n================================================================================");
        return person;

    }
}
