package org.views.admin.auth;

import org.app.Models.Helpers.PasswordUtil;

import java.util.Scanner;

public class LoginView {
    private Scanner scanner = new Scanner(System.in);

    public String[] promptForLogin() {
        System.out.println("\n=========================================");
        System.out.println("          Welcome to the Admin Panel     ");
        System.out.println("=========================================");
        System.out.print("Enter your email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine().trim();
        System.out.println(email+"\n"+password);
        System.out.println(PasswordUtil.hashPassword(password));
        return new String[]{email, password};
    }

    public static void displayLoginResult(boolean success) {
        if (success) {
            System.out.println("\n=========================================");
            System.out.println("          Lo    gin Successful!             ");
            System.out.println("=========================================");
            System.out.println("Welcome to the system!");
        } else {
            System.out.println("\n=========================================");
            System.out.println("          Login Failed                   ");
            System.out.println("=========================================");
            System.out.println("Please check your email and password.");
        }
    }

    public boolean askRetry() {
        System.out.print("Would you like to try again? (y/n): ");
        String response = scanner.nextLine().trim();
        return response.equalsIgnoreCase("y");
    }

    public void displayGoodbyeMessage() {
        System.out.println("\n=========================================");
        System.out.println("          Thank You!                    ");
        System.out.println("          Goodbye!                       ");
        System.out.println("=========================================");
    }

    public void displayErrorMessage(String message) {
        System.out.println("\n=========================================");
        System.out.println("          Error: " + message);
        System.out.println("=========================================");
    }
}
