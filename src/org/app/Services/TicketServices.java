package org.app.Services;

import org.app.Models.DAO.Admin.TicketDAO;
import org.app.Models.Entities.Ticket;
import org.views.admin.ticket.TicketView;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;


public class TicketServices {
    private TicketView ticketView;

    public TicketServices() {
        this.ticketView = new TicketView();
    }

    public void manageTicket() throws SQLException {
        while (true) {
            int choice = ticketView.operationType();

            switch (choice) {
                case 1:
                    Ticket.getTicketById();
                case 2:
                    Ticket.getAllTickets();
                    break;
                case 3:
                    Ticket.addTicket();
                    break;
                case 4:
                    Ticket.updateTicket();
                    break;
                case 5:
                    Ticket.deleteTicket();
                    break;
                case 6:
                    Ticket.restoreTicket();

                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }


}
