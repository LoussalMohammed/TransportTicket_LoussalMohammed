package org.app.Services;

import org.app.Models.Entities.Admin;
import org.app.Models.DAO.Admin.AuthDAO;
import org.views.admin.auth.LoginView;

import java.sql.SQLException;

public class AuthServices {
    private final AuthDAO authDAO;
    private final LoginView loginView;

    public AuthServices() {
        this.authDAO = new AuthDAO();
        this.loginView = new LoginView();
    }

    public boolean handleLogin() {
        boolean loggedIn = false;

        do {
            try {
                String[] credentials = loginView.promptForLogin();
                String email = credentials[0];  // Use email instead of username
                String password = credentials[1];

                // Authenticate the admin and check if logged in
                Admin admin = authDAO.authenticate(email, password);

                if (admin != null) {
                    loggedIn = true;
                    LoginView.displayLoginResult(true);

                    return true;
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
        } while (!loggedIn);

        if (!loggedIn) {
            loginView.displayGoodbyeMessage();
        }

        return false;
    }
}
