package org.app.Controllers;

import org.app.Models.Entities.Admin;
import org.app.Services.Admin.AuthServices;
import org.views.admin.auth.LoginView;

import java.sql.SQLException;

public class AuthController {
    private final AuthServices authService;
    private final LoginView loginView;

    public AuthController() {
        this.authService = new AuthServices();
        this.loginView = new LoginView();
    }

    public void handleLogin() {
        boolean loggedIn = false;

        do {
            try {
                String[] credentials = loginView.promptForLogin();
                String email = credentials[0];  // Use email instead of username
                String password = credentials[1];

                // Authenticate the admin and check if logged in
                Admin admin = authService.authenticate(email, password);

                if (admin != null) {
                    loggedIn = true;
                    LoginView.displayLoginResult(true);
                    // You can proceed with logged-in user actions here
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
    }
}
