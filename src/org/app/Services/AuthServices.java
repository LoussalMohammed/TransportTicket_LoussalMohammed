package org.app.Services;

import org.app.Models.DAO.Admin.PersonDAO;
import org.app.Models.Entities.Admin;
import org.app.Models.DAO.Admin.AuthDAO;
import org.app.Models.Entities.Person;
import org.app.Models.Enums.Role;
import org.views.admin.auth.LoginView;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthServices {
    private final AuthDAO authDAO;
    private final LoginView loginView;
    private final PersonDAO personDAO;

    public AuthServices() {
        this.authDAO = new AuthDAO();
        this.loginView = new LoginView();
        this.personDAO = new PersonDAO();
    }

    public ArrayList<HashMap> handleLogin() throws SQLException {
        boolean loggedIn = false;

        int typeAuth = loginView.AuthType();
        do {
            if(typeAuth == 1) {
                try {
                    String[] credentials = loginView.promptForLogin();
                    String email = credentials[0];  // Use email instead of username
                    String password = credentials[1];

                    // Authenticate the admin and check if logged in
                    Person person = authDAO.authenticate(email, password);

                    if (person != null) {
                        loggedIn = true;
                        LoginView.displayLoginResult(true);
                        List<HashMap> loginSuccess = new ArrayList();
                        HashMap<String, Boolean> login = new HashMap<>();
                        login.put("loggedIn", true);
                        HashMap<String, Person> personMap = new HashMap<>();
                        personMap.put("person", person);
                        loginSuccess.add(login);
                        loginSuccess.add(personMap);
                        return (ArrayList<HashMap>) loginSuccess;
                    } else {
                        LoginView.displayLoginResult(false);
                    }

                    // Ask if the user wants to retry if not logged in
                    if (!loggedIn && !loginView.askRetry()) {
                        break;
                    }
                } catch (SQLException e) {
                    // Handle SQL exceptions, such as connection errors
                    System.err.println("An error occurred during login: " + e.getMessage());
                    // Optionally, show an error message to the user
                    loginView.displayErrorMessage("Login failed due to a system error. Please try again later.");
                    break;
                }

            }
            else if (typeAuth == 2) {
                Map<String, String> credentials = loginView.promptForSignUp();
                Integer id = personDAO.getLastId() + 1;


                // Create User:
                Person person = new Person(id, credentials.get("firstName"), credentials.get("lastName"), credentials.get("email"), credentials.get("phone"), Role.USER, credentials.get("password"), LocalDateTime.now());

                try {
                    personDAO.save(person);
                    String[] loginCredentials = loginView.promptForLogin();
                    String email = loginCredentials[0];
                    String password = loginCredentials[1];

                    Person personLogin = authDAO.authenticate(email, password);

                    if (personLogin != null) {
                        loggedIn = true;
                        LoginView.displayLoginResult(true);
                        LoginView.displayLoginResult(true);
                        List<HashMap> loginSuccess = new ArrayList();
                        HashMap<String, Boolean> login = new HashMap<>();
                        login.put("loggedIn", true);
                        HashMap<String, Person> personMap = new HashMap<>();
                        personMap.put("person", person);
                        loginSuccess.add(login);
                        loginSuccess.add(personMap);

                        return (ArrayList<HashMap>) loginSuccess;
                    } else {
                        LoginView.displayLoginResult(false);
                    }

                    // Ask if the user wants to retry if not logged in
                    if (!loggedIn && !loginView.askRetry()) {
                        break;
                    }
                } catch (SQLException e) {
                    System.err.println(e);
                }


            }

        } while (!loggedIn);

        if (!loggedIn) {
            loginView.displayGoodbyeMessage();
        }

        return null;
    }
}
