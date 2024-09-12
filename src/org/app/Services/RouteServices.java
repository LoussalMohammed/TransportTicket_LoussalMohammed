package org.app.Services;

import org.app.Models.Entities.Route;
import org.app.Models.Entities.Ticket;
import org.views.admin.route.RouteView;
import org.views.admin.ticket.TicketView;

import java.sql.SQLException;

public class RouteServices {
    private RouteView routeView;

    public RouteServices() {
        this.routeView = new RouteView();
    }

    public void manageRoutes() throws SQLException {
        while (true) {
            int choice = routeView.operationType();

            switch (choice) {
                case 1:
                    Route.getRouteById();
                case 2:
                    Route.getAllRoutes();
                    break;
                case 3:
                    Route.addRoute();
                    break;
                case 4:
                    Route.updateRoute();
                    break;
                case 5:
                    Route.deleteRoute();
                    break;
                case 6:
                    Route.restoreRoute();

                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
