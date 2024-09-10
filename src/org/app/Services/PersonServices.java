package org.app.Services;

import org.app.Models.DAO.Admin.PersonDAO;
import org.app.Models.Entities.Person;
import org.views.admin.person.PersonView;

import java.sql.SQLException;
import java.util.Scanner;

public class PersonServices {
    private final PersonDAO personDAO;
    private final PersonView personView;

    public PersonServices() {
        this.personDAO = new PersonDAO();
        this.personView = new PersonView();
        Scanner scanner = new Scanner(System.in);
    }


    public void managePersons() throws SQLException {
        boolean continueManaging = true;

        while (continueManaging) {
            int action = personView.operationType();

            switch (action) {
                case 1:
                    Person.getPersonById();
                    break;
                case 2:
                    Person.getAllPersons();
                    break;
                case 3:
                    Person.addPerson();
                    break;
                case 4:
                    Person.updatePerson();
                    break;
                case 5:
                    Person.deletePerson();
                    break;
                case 6:
                    Person.restorePerson();
                case 0:
                    continueManaging = false;
                    System.out.println("Exiting Person management.");
                    break;
                default:
                    System.out.println("Invalid option. Please choose a valid action.");
                    break;
            }
        }
    }

}
