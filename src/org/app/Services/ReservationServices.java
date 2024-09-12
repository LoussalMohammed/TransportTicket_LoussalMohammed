package org.app.Services;

import org.app.Models.Entities.Person;
import org.app.Models.Entities.Reservation;
import org.app.Models.Entities.Ticket;
import org.views.client.reservation.ReservationView;

import java.sql.SQLException;

public class ReservationServices {
    private ReservationView reservationView;

    public ReservationServices() {
        this.reservationView = new ReservationView();
    }

    public void manageReservation(Person person) throws SQLException {
        while (true) {
            int choice = reservationView.operationType();

            switch (choice) {
                case 1:
                    Reservation.getAllReservations();
                    break;
                case 2:
                    Reservation.getReservationById();
                    break;
                case 3:
                    Reservation.addReservation(person);
                    break;
                /*case 4:
                    Reservation.updateTicket();
                    break;
                case 5:
                    Reservation.deleteTicket();
                    break;

                 */
                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }


}
