package org.views.client.reservation;
import org.app.Models.DAO.Person.ReservationDAO;
import org.app.Models.Entities.Person;
import org.app.Models.Entities.Reservation;
import org.app.Models.Helpers.LevenshteinDistance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ReservationView {
    private final ReservationDAO reservationDAO;
    private final Scanner scanner;

    public ReservationView() {
        this.reservationDAO = new ReservationDAO();
        this.scanner = new Scanner(System.in);
    }

    public int operationType() {
        System.out.println("\n=====================================================================================================================================");
        System.out.println("                                             Welcome to the Reservation Management System                                           ");
        System.out.println("=====================================================================================================================================");
        System.out.println("Choose an operation:");
        System.out.println("1. List Your Past Reservations");
        System.out.println("2. List A Specific Reservation");
        System.out.println("3. Reserve a Ticket");
        System.out.println("4. Cancel a Reservation");
        System.out.println("0. Exit");
        System.out.println("\nEnter your choice (0-4): ");
        return scanner.nextInt();
    }

    public int getReservationId() {
        System.out.println("\nPlease enter the Reservation ID:");
        return scanner.nextInt();
    }

    public void displayReservation(Reservation reservation) {
        Optional<Reservation> reservationOptional = Optional.ofNullable(reservation);
        if(reservationOptional.isPresent()) {
            System.out.println("\n=====================================================================================================================================");
            System.out.println("|      Reservation ID      |        Ticket ID        |      Client ID       |   Partner ID  |");
            System.out.println("=====================================================================================================================================");
            System.out.printf("| %21s | %21s | %21s | %8s |\n",
                    reservation.getId(), reservation.getTicketId(), reservation.getClientId(), reservation.getPartnerId());
            System.out.println("=====================================================================================================================================\n");
        }else {
            System.out.println("There Is No Reservation With This ID!");
        }

    }

    public void displayReservationsList(List<Reservation> reservations) {
        Optional<List<Reservation>> reservationsOptional = Optional.ofNullable(reservations);
        if(reservations != null && reservationsOptional.isPresent() && !reservationsOptional.isEmpty()) {
            System.out.println("\n=====================================================================================================================================");
            System.out.println("|      Reservation ID      |        Ticket ID        |      Client ID       |   Partner ID  |");
            System.out.println("=====================================================================================================================================");
            reservations.stream()
                            .forEach(reservation -> {
                                System.out.printf("| %21s | %21s | %21s | %8s |\n",
                                        reservation.getId(), reservation.getTicketId(), reservation.getClientId(), reservation.getPartnerId());
                            });
            System.out.println("=====================================================================================================================================\n");
        } else {
            System.out.println("There Is No Reservations Yet!");
        }

    }

    public List<Object> ReserveTicket(List<List<Object>> tickets) {
        System.out.println("_______________________________________________________________________");
        System.out.println("\t||   You Found The Ticket You Want? Then Enter The Ticket Id:  ||\t");
        System.out.println("________________________________________________________________________");

        System.out.println("IF Yes Then Enter Yes Otherwise No:");
        String answer = scanner.nextLine();
        if(LevenshteinDistance.compute_Levenshtein_distanceDP(answer, "Yes") <= 2) {

            System.out.println("\t Then Enter The Ticket Id: \t");

            UUID ticketId = UUID.fromString(scanner.nextLine());

            // Find the first ticket that matches the entered ticketId
            return tickets.stream()
                    .filter(chosenTicket -> {
                        String cticketId = chosenTicket.get(0).toString();
                        UUID cticketUUId = UUID.fromString(cticketId);
                        return cticketUUId.equals(ticketId);
                    })
                    .findFirst() // Find the first matching ticket
                    .orElse(null); // Return null if no ticket is found
        } else {
            return null;
        }


    }


    public void getTickets(List<List<Object>> tickets) {
        Optional<List<List<Object>>> ticketsOptional = Optional.ofNullable(tickets);
        if(tickets != null && ticketsOptional.isPresent() && !ticketsOptional.isEmpty()) {
            System.out.println("\n======================================================================================================================================================================");
            System.out.println(" |      Ticket ID      |      Transport Type      |        Transporter Name        |       Departure Date       |   Price  |           Partner ID            |");
            System.out.println("========================================================================================================================================================================");
            ticketsOptional.stream()
                    .forEach(mTickets -> {
                        mTickets.stream()
                                        .forEach(sTicket  -> {
                                            System.out.printf("| %28s | %21s | %21s | %21s | %15s | %24s |\n",
                                                    sTicket.get(0), sTicket.get(1), sTicket.get(2), sTicket.get(3), sTicket.get(4), sTicket.get(5));
                                        });

                    });
            System.out.println("=======================================================================================================================================================================\n");
        } else {
            System.out.println("There Is No Tickets That Met Your Specifications Yet!");
        }
    }

    public List<Object> addReservationSpecifications() {
        System.out.println("\n===========================================================");
        System.out.println("                        Reserve a Ticket                     ");
        System.out.println("=============================================================");
        if(scanner.hasNextLine()) {
            scanner.nextLine();
        }
        System.out.println("Enter The Departure City:\t");
        String departureCity = scanner.nextLine();

        System.out.println("Enter The Destination City:\t");
        String destinationCity = scanner.nextLine();
        while (departureCity == destinationCity){
            System.out.println("Departure City And Can't Be The Same Destination City!");
            System.out.println("Enter The Destination City:\t");
            destinationCity = scanner.nextLine();
        }

        System.out.println("Enter The Departure Date:\t");
        LocalDateTime departureDate = LocalDateTime.parse(scanner.nextLine());

        List<Object> ticketFilter = new ArrayList<>();
        ticketFilter.add(departureCity);
        ticketFilter.add(destinationCity);
        ticketFilter.add(departureDate);

        return ticketFilter;
    }

    public int cancelReservation(Person person) {
        try {
            System.out.print("Enter Reservation ID to cancel: ");
            int reservationId = scanner.nextInt();
            Reservation existingReservation = reservationDAO.findById(reservationId, person.getId());
            if (existingReservation == null) {
                System.out.println("Reservation not found.");
                return 0;
            } else {
                return reservationId;
            }

        } catch (Exception e) {
            System.out.println("Error canceling reservation: " + e.getMessage());
        }
        return 0;
    }

    public void cancellationDone() {
        System.out.println("Reservation Canceled Successfully!");
    }

    /*
    public void listReservationsByClient(String clientId) {
        List<Reservation> reservations = reservationDAO.findByClientId(clientId);
        if (reservations.isEmpty()) {
            System.out.println("No reservations found for this client.");
        } else {
            displayReservationsList(reservations);
        }
    }*/
}
