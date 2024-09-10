package org.views.admin.auth;

import org.app.Models.Helpers.PasswordUtil;

import java.util.Scanner;

public class LoginView {
    private Scanner scanner = new Scanner(System.in);

    public String[] promptForLogin() {
        System.out.println("\n================================================================");
        System.out.println("                     Welcome to the Login Panel               ");
        System.out.println("==================================================================");
        System.out.print("Enter your email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine().trim();
        return new String[]{email, password};
    }

    public String[] promptForSignUp() {
        System.out.println("\n================================================================");
        System.out.println("                     Welcome to the SignUp Panel             ");
        System.out.println("==================================================================");
        System.out.print("Enter your First Name: ");
        String firstName = scanner.nextLine().trim();
        System.out.print("Enter your Last Name: ");
        String lastName = scanner.nextLine().trim();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine().trim();
        return new String[]{firstName, lastName,email, password};
    }



    public Integer AuthType() {
        System.out.println("\n================================================================");
        System.out.println("                      Welcome to the Auth Panel             ");
        System.out.println("==================================================================");
        Integer choice = 0;
        do {
            System.out.println("\nEnter 1 => Login, OR Enter 2 => SignUp:\t");
            choice = scanner.nextInt();
        } while(choice < 1 || choice > 2);
        return choice;

    }

    public static void displayLoginResult(boolean success) {
        if (success) {
            System.out.println("\n================================================================");
            System.out.println("                   Login Successful!                     ");
            System.out.println("==================================================================");
            System.out.println("Welcome to the system!");
        } else {
            System.out.println("\n================================================================");
            System.out.println("                   Login Failed                     ");
            System.out.println("==================================================================");
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
