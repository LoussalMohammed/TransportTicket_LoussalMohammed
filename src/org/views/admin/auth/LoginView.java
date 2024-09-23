package org.views.admin.auth;

import org.app.Models.Helpers.PasswordUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LoginView {
    private Scanner scanner = new Scanner(System.in);

    public String[] promptForLogin() {
        System.out.println("\n================================================================");
        System.out.println("                     Welcome to the Login Panel               ");
        System.out.println("==================================================================");
        System.out.print("Enter your email: ");
        String email = scanner.nextLine().trim();
        if(scanner.hasNextLine()) {
            scanner.nextLine();
        }
        System.out.print("Enter your password: ");
        String password = scanner.nextLine().trim();
        return new String[]{email, password};
    }

    public Map<String, String> promptForSignUp() {
        Map<String, String> userData = new HashMap<>();
        System.out.println("\n================================================================");
        System.out.println("                     Welcome to the SignUp Panel             ");
        System.out.println("==================================================================\n");
        scanner.nextLine();
        System.out.print("Enter your First Name: ");
        userData.put("firstName", scanner.nextLine().trim());
        System.out.print("Enter your Last Name: ");
        userData.put("lastName", scanner.nextLine().trim());
        System.out.print("Enter your email: ");
        userData.put("email", scanner.nextLine().trim());
        System.out.print("Enter your phone Number: ");
        userData.put("phone", scanner.nextLine().trim());
        System.out.print("Enter your password: ");
        userData.put("password", scanner.nextLine().trim());

        return userData;
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
        if(scanner.hasNextLine()) {
            scanner.nextLine();
        }
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
