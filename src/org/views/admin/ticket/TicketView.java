package org.views.admin.ticket;

import org.app.Models.Entities.Contract;
import org.app.Models.Entities.Ticket;
import org.app.Models.Enums.Transport;
import org.app.Models.Enums.TicketStatus;
import org.app.Models.DAO.Admin.TicketDAO;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class TicketView {
    private final TicketDAO ticketDAO;
    private final Scanner scanner;

    public TicketView() {
        this.ticketDAO = new TicketDAO();
        this.scanner = new Scanner(System.in);
    }

    public int operationType() {
            System.out.println("\n=====================================================================================================================================");
            System.out.println("                                             Welcome to the Ticket Entity Management                                          ");
            System.out.println("=====================================================================================================================================");
            System.out.println("Choose an operation:");
            System.out.println("1. List a Single Ticket");
            System.out.println("2. List All Ticket");
            System.out.println("3. Add a New Ticket");
            System.out.println("4. Update an Existing Ticket");
            System.out.println("5. Delete a Ticket");
            System.out.println("6. Restore a Ticket");
            System.out.println("0. Exit");
            System.out.println("\nEnter your choice (0-5): ");
            return scanner.nextInt();

    }

    public UUID getTicketId() {
        System.out.println("\nPlease enter the Ticket ID (UUID format):");
        return UUID.fromString(scanner.next());
    }

    public void displayTicket(Ticket ticket) {
        System.out.println("\n=====================================================================================================================================");
        System.out.println("|        Ticket ID        |     Transport Type     |    Buying Price    |    Selling Price    |   Selling Date   |   Status  |");
        System.out.println("=====================================================================================================================================");
        System.out.printf("| %21s | %20s | %18s | %18s | %17s | %8s |\n",
                ticket.getId(), ticket.getTransportType(), ticket.getBuyingPrice(), ticket.getSellingPrice(), ticket.getSellingDate(), ticket.getTicketStatus());
        System.out.println("=====================================================================================================================================\n");
    }

    public void displayTicketsList(List<Ticket> tickets) {
        System.out.println("\n=====================================================================================================================================");
        System.out.println("|        Ticket ID        |     Transport Type     |    Buying Price    |    Selling Price    |   Selling Date   |   Status  |");
        System.out.println("=====================================================================================================================================");
        for (Ticket ticket : tickets) {
            System.out.printf("| %21s | %20s | %18s | %18s | %17s | %8s |\n",
                    ticket.getId(), ticket.getTransportType(), ticket.getBuyingPrice(), ticket.getSellingPrice(), ticket.getSellingDate(), ticket.getTicketStatus());
        }
        System.out.println("=====================================================================================================================================\n");
    }

    public Ticket addTicket() {
        System.out.println("\n=========================");
        System.out.println("      Add a New Ticket    ");
        System.out.println("=========================\n");

        UUID ticketId = UUID.randomUUID();

        System.out.print("Enter Buying Price: ");
        BigDecimal buyingPrice = BigDecimal.valueOf(Double.parseDouble(scanner.nextLine()));

        System.out.print("Enter Selling Price: ");
        BigDecimal sellingPrice = BigDecimal.valueOf(Double.parseDouble(scanner.nextLine()));

        System.out.print("Enter Selling Date (yyyy-mm-dd): ");
        String sellingDateStr = scanner.nextLine();
        java.util.Date sellingDate = java.sql.Date.valueOf(sellingDateStr);

        Transport transport = null;
        while (transport == null) {
            System.out.print("Select Transport Type BUS => 1, Train => 2, Airplane => 3: ");
            int Tt = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over
            transport = Tt == 1 ? Transport.BUS : Tt == 2 ? Transport.TRAIN : Tt == 3 ? Transport.AIRPLANE : null;

            if (transport == null) {
                System.out.println("Invalid selection. Please try again.");
            }
        }

        UUID contractId = null;
        while (contractId == null) {
            System.out.print("Select Contract ID:\t");
            String ci = scanner.nextLine();

            try {
                contractId = UUID.fromString(ci);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid UUID format. Please enter a valid Contract ID.");
            }
        }

        TicketStatus ticketStatus = null;
        while (ticketStatus == null) {
            System.out.print("Select Ticket Status Type SOLD => 1, CANCELED => 2, WAITING => 3: ");
            int Ts = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over
            ticketStatus = Ts == 1 ? TicketStatus.SOLD : Ts == 2 ? TicketStatus.CANCELED : Ts == 3 ? TicketStatus.WAITING : null;

            if (ticketStatus == null) {
                System.out.println("Invalid selection. Please try again.");
            }
        }

        Ticket ticket = new Ticket(ticketId, transport, buyingPrice, sellingPrice, sellingDate, ticketStatus, contractId);
        return ticket;
    }

    public void updateTicket() {
        try {
            System.out.print("Enter ticket ID to update: ");
            UUID ticketId = UUID.fromString(scanner.nextLine());

            Ticket existingTicket = ticketDAO.findById(ticketId);
            if (existingTicket == null) {
                System.out.println("Ticket not found.");
                return;
            }

            // Updating transport type
            Transport transport = null;
            while (transport == null) {
                System.out.print("Select Transport Type BUS => 1, Train => 2, Airplane => 3: ");
                int Tt = scanner.nextInt();
                scanner.nextLine(); // Consume newline left-over
                transport = Tt == 1 ? Transport.BUS : Tt == 2 ? Transport.TRAIN : Tt == 3 ? Transport.AIRPLANE : null;

                if (transport == null) {
                    System.out.println("Invalid selection. Please try again.");
                }
            }
            existingTicket.setTransportType(transport);

            // Updating buying price
            System.out.print("Enter new buying price [" + existingTicket.getBuyingPrice() + "]: ");
            String buyingPriceInput = scanner.nextLine();
            if (!buyingPriceInput.isEmpty()) {
                existingTicket.setBuyingPrice(BigDecimal.valueOf(Double.parseDouble(buyingPriceInput)));
            }

            // Updating selling price
            System.out.print("Enter new selling price [" + existingTicket.getSellingPrice() + "]: ");
            String sellingPriceInput = scanner.nextLine();
            if (!sellingPriceInput.isEmpty()) {
                existingTicket.setSellingPrice(BigDecimal.valueOf(Double.parseDouble(sellingPriceInput)));
            }

            // Updating selling date
            System.out.print("Enter new selling date (yyyy-mm-dd) [" + existingTicket.getSellingDate() + "]: ");
            String sellingDateInput = scanner.nextLine();
            if (!sellingDateInput.isEmpty()) {
                existingTicket.setSellingDate(java.sql.Date.valueOf(sellingDateInput));
            }

            // Updating contract ID
            UUID contractId = null;
            while (contractId == null) {
                System.out.print("Enter new Contract ID [" + existingTicket.getContract_id() + "]: ");
                String ci = scanner.nextLine();

                if (ci.isEmpty()) {
                    contractId = existingTicket.getContract_id(); // Keep the existing ID if no new input
                } else {
                    try {
                        contractId = UUID.fromString(ci);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid UUID format. Please enter a valid Contract ID.");
                    }
                }
            }
            existingTicket.setContract_id(contractId);

            // Updating ticket status
            TicketStatus ticketStatus = null;
            while (ticketStatus == null) {
                System.out.print("Select new Ticket Status Type SOLD => 1, CANCELED => 2, WAITING => 3 [" + existingTicket.getTicketStatus() + "]: ");
                int Ts = scanner.nextInt();
                scanner.nextLine(); // Consume newline left-over
                ticketStatus = Ts == 1 ? TicketStatus.SOLD : Ts == 2 ? TicketStatus.CANCELED : Ts == 3 ? TicketStatus.WAITING : null;

                if (ticketStatus == null) {
                    System.out.println("Invalid selection. Please try again.");
                }
            }
            existingTicket.setTicketStatus(ticketStatus);

            // Update the ticket in the database
            ticketDAO.update(existingTicket);
            System.out.println("Ticket updated successfully.");
        } catch (Exception e) {
            System.out.println("Error updating ticket: " + e.getMessage());
        }
    }


    public void deleteTicket() throws SQLException {
        System.out.print("Enter ticket ID to restore (UUID format): ");
        UUID deleteTicketId = UUID.fromString(scanner.nextLine().trim());
        Ticket ticketToDelete = ticketDAO.findById(deleteTicketId);
        if (ticketToDelete != null) {
            ticketDAO.delete(deleteTicketId);
            System.out.println("Ticket successfully deleted.");
        } else {
            System.out.println("Ticket not found.");
        }
    }



    public void restoreTicket() throws SQLException {
        System.out.print("Enter ticket ID to restore (UUID format): ");
        UUID restoreTicketId = UUID.fromString(scanner.nextLine().trim());
        Ticket ticketToRestore = ticketDAO.findById(restoreTicketId);
        if (ticketToRestore != null) {
            ticketDAO.restore(restoreTicketId);
            System.out.println("Ticket successfully Restored.");
        } else {
            System.out.println("Ticket not found.");
        }
    }

}
