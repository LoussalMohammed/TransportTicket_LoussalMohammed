package org.views;

import org.app.Models.Entities.Person;
import org.app.Models.Enums.Role;
import org.app.Services.*;
import org.views.client.reservation.ReservationView;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        AuthServices authServices = new AuthServices();
        List<HashMap> loggedIn = authServices.handleLogin();
        if (loggedIn != null) {

            Person person = (Person) loggedIn.get(1).get("person");
            if (person.getRole() == Role.ADMIN) {
                boolean continueChoosing = true; // Flag to control the loop

                while (continueChoosing) {
                    System.out.println("\n=============================================================================================================================================");
                    System.out.println("Choose the wanted Entity: person => 1 admin => 2, partner => 3, contract => 4, promotional Offer => 5, route => 6, ticket => 7, exit => 0");
                    System.out.println("===============================================================================================================================================");

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
                            RouteServices routeServices = new RouteServices();
                            routeServices.manageRoutes();
                            break;
                        case 7:
                            TicketServices ticketServices = new TicketServices();
                            ticketServices.manageTicket();
                            break;
                        case 0:
                            continueChoosing = false;
                            System.out.println("Exiting the selection process.");
                            break;
                        default:
                            System.out.println("Invalid option. Please choose a number between 0 and 5.");
                            break; // This exits the switch statement
                    }
                }
            } else {
                ReservationView reservationView = new ReservationView();

                boolean continueChoosing = true; // Flag to control the loop

                while (continueChoosing) {
                    System.out.println("\n=============================================================================================================================================");
                    System.out.println("                             WELCOME TO ECOMOVE. The Best Transportation Agency Online                        ");
                    System.out.println("===============================================================================================================================================");

                    System.out.println("\n=============================================================================================================================================");
                    System.out.println("                             With ECOMOVE You Can Find and Reserve Your Tickets To Your Chosen Land                           ");
                    System.out.println("===============================================================================================================================================");


                    ReservationServices reservationServices = new ReservationServices();
                    reservationServices.manageReservation();


                }

            }


        }
    }
}
