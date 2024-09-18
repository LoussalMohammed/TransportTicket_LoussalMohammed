package org.views.admin.route;

import org.app.Models.DAO.Admin.RouteDAO;
import org.app.Models.Entities.Route;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class RouteView {
    private final RouteDAO routeDAO;
    private final Scanner scanner;

    public RouteView() {
        this.routeDAO = new RouteDAO();
        this.scanner = new Scanner(System.in);
    }

    public int operationType() {
        System.out.println("\n=====================================================================================================================================");
        System.out.println("                                             Welcome to the Route Entity Management                                          ");
        System.out.println("=====================================================================================================================================");
        System.out.println("Choose an operation:");
        System.out.println("1. List a Single Route");
        System.out.println("2. List All Routes");
        System.out.println("3. Add a New Ticket");
        System.out.println("4. Update an Existing Route");
        System.out.println("5. Delete a Route");
        System.out.println("6. Restore a Route");
        System.out.println("0. Exit");
        System.out.println("\nEnter your choice (0-5): ");
        return scanner.nextInt();

    }

    public int getRouteId() {
        System.out.println("\nPlease enter the Route ID (UUID format):");
        return scanner.nextInt();
    }

    public void displayRoute(Route route) {
        System.out.println("\n==============================================================================================================================================");
        System.out.println("|        Route ID        |     Depart City     |    Destination City    |    Depart Date    |   Arrival Date   |   Price   |  partnerId  |");
        System.out.println("================================================================================================================================================");
        System.out.printf("| %12s | %20s | %18s | %18s | %18s | %9s | %18s |\n",
                route.getId(), route.getDepartureCity(), route.getDestinationCity(), route.getDepartureDate(), route.getArrivalDate(), route.getPrice(), route.getPartnerId());
        System.out.println("=====================================================================================================================================\n");
    }

    public void displayRoutesList(List<Route> routes) {
        System.out.println("\n=====================================================================================================================================");
        System.out.println("|        Route ID        |     Depart City     |    Destination City    |    Depart Date    |   Arrival Date   |   Price   |  partnerId  |");
        System.out.println("=====================================================================================================================================");
        routes.stream()
                .forEach(route -> {
                    System.out.printf("| %12s | %20s | %18s | %18s | %18s | %9s | %18s |\n",
                            route.getId(), route.getDepartureCity(), route.getDestinationCity(), route.getDepartureDate(), route.getArrivalDate(), route.getPrice(), route.getPartnerId());
                });
        System.out.println("=====================================================================================================================================\n");
    }

    public Route addRoute() {
        System.out.println("\n=========================================");
        System.out.println("             Add a New Route            ");
        System.out.println("=========================================\n");

        int id = RouteDAO.getLastId()+1;

        System.out.print("Enter Departure City: ");
        String departureCity = scanner.nextLine();

        String destinationCity;
        System.out.print("Enter Destination City: ");
        destinationCity = scanner.nextLine();
        do {
            System.out.print("Destination City Can't Be The Same As Departure City: ");
            System.out.print("Enter Destination City: ");
            destinationCity = scanner.nextLine();
        } while (destinationCity == departureCity);


        System.out.print("Enter Departure Date (Format EX: 2020-10-09T08:30:00): ");
        LocalDateTime departureDate = LocalDateTime.parse(scanner.nextLine());
        while(LocalDateTime.now().isAfter(departureDate)) {
            System.out.print("Enter Departure Date (Format EX: 2020-10-09T08:30:00) AND It Should be after ("+LocalDateTime.now()+"):");
            departureDate = LocalDateTime.parse(scanner.nextLine());
        }

        System.out.print("Enter Arrival Date (Format EX: 2020-10-09T08:30:00): ");
        LocalDateTime arrivalDate = LocalDateTime.parse(scanner.nextLine());
        while(LocalDateTime.now().isAfter(arrivalDate)) {
            System.out.print("Enter Arrival Date (Format EX: 2020-10-09T08:30:00) AND It Should be after ("+LocalDateTime.now()+"):");
            arrivalDate = LocalDateTime.parse(scanner.nextLine());
        }

        System.out.println("Enter Price (34.99):");
        BigDecimal price = scanner.nextBigDecimal();
        UUID partnerId = null;
        while (partnerId == null) {
            System.out.print("Select Partner ID (UUID Format):\t");
            String pi = scanner.nextLine();

            try {
                partnerId = UUID.fromString(pi);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid UUID format. Please enter a valid Partner ID.");
            }
        }

        Route route = new Route(id, departureCity, destinationCity, departureDate, arrivalDate, price, partnerId);
        return route;
    }

    public Route updateRoute() {
        try {
            System.out.print("Enter Route ID to update: ");
            int routeId = scanner.nextInt();
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            Route existingRoute = routeDAO.findById(routeId);
            if (existingRoute == null) {
                System.out.println("Route not found.");
                return null;
            }

            System.out.println("Current Departure City: " + existingRoute.getDepartureCity());
            System.out.println("Enter new Departure City (or press Enter to keep current): ");
            String departureCity = scanner.nextLine();
            if (!departureCity.isEmpty()) {
                existingRoute.setDepartureCity(departureCity);
            }

            System.out.println("Current Destination City: " + existingRoute.getDestinationCity());
            System.out.println("Enter new Destination City (or press Enter to keep current): ");
            String destinationCity = scanner.nextLine();
            if (!destinationCity.isEmpty()) {
                existingRoute.setDestinationCity(destinationCity);
            }

            System.out.println("Current Departure Date: " + existingRoute.getDepartureDate());
            System.out.println("Enter new Departure Date (YYYY-MM-DDTHH:MM) (or press Enter to keep current): ");
            String departureDate = scanner.nextLine();
            if (!departureDate.isEmpty()) {
                existingRoute.setDepartureDate(LocalDateTime.parse(departureDate));
            }

            System.out.println("Current Arrival Date: " + existingRoute.getArrivalDate());
            System.out.println("Enter new Arrival Date (YYYY-MM-DDTHH:MM) (or press Enter to keep current): ");
            String arrivalDate = scanner.nextLine();
            if (!arrivalDate.isEmpty()) {
                existingRoute.setArrivalDate(LocalDateTime.parse(arrivalDate));
            }

            System.out.println("Current Price: " + existingRoute.getPrice());
            System.out.println("Enter new Price (or press Enter to keep current): ");
            String priceInput = scanner.nextLine();
            if (!priceInput.isEmpty()) {
                BigDecimal price = new BigDecimal(priceInput);
                existingRoute.setPrice(price);
            }

            UUID partnerId = null;
            while (partnerId == null) {
                System.out.print("Enter new Partner ID [" + existingRoute.getPartnerId() + "] or press Enter to keep current: ");
                String pi = scanner.nextLine();
                if (pi.isEmpty()) {
                    partnerId = existingRoute.getPartnerId();  // Keep existing ID
                } else {
                    try {
                        partnerId = UUID.fromString(pi);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid UUID format. Please enter a valid Partner ID.");
                    }
                }
            }

            existingRoute.setPartnerId(partnerId);

            return existingRoute;
        } catch (Exception e) {
            System.out.println("Error updating Route: " + e.getMessage());
        }
        return null;
    }



    public void deleteTicket() throws SQLException {
        System.out.print("Enter Route ID to restore: ");
        int deleteRouteId = scanner.nextInt();
        Route routeToDelete = routeDAO.findById(deleteRouteId);
        if (routeToDelete != null) {
            routeDAO.delete(deleteRouteId);
            System.out.println("Route successfully deleted.");
        } else {
            System.out.println("Route not found.");
        }
    }



    public void restoreRoute() throws SQLException {
        System.out.print("Enter Route ID to restore (UUID format): ");
        int restoreRouteId = scanner.nextInt();
        Route routeToRestore = routeDAO.findById(restoreRouteId);
        if (routeToRestore != null) {
            routeDAO.restore(restoreRouteId);
            System.out.println("Route successfully Restored.");
        } else {
            System.out.println("Route not found.");
        }
    }
}
