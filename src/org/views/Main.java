package org.views;

import org.app.Services.*;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        AuthServices authServices = new AuthServices();
        boolean loggedIn = authServices.handleLogin();
        if (loggedIn) {
            boolean continueChoosing = true; // Flag to control the loop

            while (continueChoosing) {
                System.out.println("\n=================================================================================================================");
                System.out.println("Choose the wanted Entity: person => 1 admin => 2, partner => 3, contract => 4, promotional Offer => 5, ticket => 6, exit => 0");
                System.out.println("=================================================================================================================== ");

                Integer entity = scanner.nextInt();

                switch (entity) {
                    case 1:
                        PersonServices personServices = new PersonServices();
                        personServices.managePersons();
                        break;
                    case 2:
                        AdminServices adminServices = new AdminServices();
                        adminServices.manageAdmins();
                        break;
                    case 3:
                        PartnerServices partnerServices = new PartnerServices();
                        partnerServices.managePartner();
                        break;
                    case 4:
                        ContractServices contractServices = new ContractServices();
                        contractServices.manageContract();
                        break;
                    case 5:
                        PromotionalOfferServices promotionalOfferServices = new PromotionalOfferServices();
                        promotionalOfferServices.managePromotionalOffer();
                        break;
                    case 6:
                        TicketServices ticketServices = new TicketServices();
                        ticketServices.manageTicket();
                    case 0:
                        continueChoosing = false;
                        System.out.println("Exiting the selection process.");
                        break;
                    default:
                        System.out.println("Invalid option. Please choose a number between 0 and 5.");
                        break; // This exits the switch statement
                }
            }
        }


    }
}
