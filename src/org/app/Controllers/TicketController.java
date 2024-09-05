package org.app.Controllers;

import org.app.Models.Entities.Ticket;
import org.app.Services.Admin.TicketServices;
import org.views.admin.ticket.TicketView;
import org.views.admin.ticket.TicketView.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;


public class TicketController {
    private TicketView ticketView;
    private Scanner scanner;

    public TicketController() {
        this.ticketView = new TicketView();
        this.scanner = new Scanner(System.in);
    }

    public void manageTicket() throws SQLException {
        while (true) {
            int choice = ticketView.operationType();

            switch (choice) {
                case 1:
                    java.util.UUID ticketID = ticketView.getTicketId();
                    TicketServices ticketServices = new TicketServices();
                    Ticket ticket = ticketServices.findById(ticketID);
                    ticketView.displayTicket(ticket);
                case 2:
                    TicketServices ticketServicesList = new TicketServices();
                    List<Ticket> tickets = ticketServicesList.getTickets();
                    ticketView.displayTicketsList(tickets);
                    break;
                case 3:
                    ticketView.addTicket();
                    break;
                case 4:
                    ticketView.updateTicket();
                    break;
                case 5:
                    System.out.print("Enter ticket ID to delete (UUID format): ");
                    UUID deleteTicketId = UUID.fromString(scanner.nextLine().trim());
                    TicketServices ticketServices1 = new TicketServices();
                    Ticket ticketToDelete = ticketServices1.findById(deleteTicketId);
                    if (ticketToDelete != null) {
                        ticketServices1.delete(deleteTicketId);
                        System.out.println("Ticket successfully deleted.");
                    } else {
                        System.out.println("Ticket not found.");
                    }
                    break;
                case 6:
                    System.out.print("Enter ticket ID to restore (UUID format): ");
                    UUID restoreTicketId = UUID.fromString(scanner.nextLine().trim());
                    TicketServices ticketServices2 = new TicketServices();
                    ticketServices2.delete(restoreTicketId);
                    System.out.println("Ticket successfully restored.");

                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
