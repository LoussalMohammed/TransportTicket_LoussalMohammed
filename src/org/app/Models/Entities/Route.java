package org.app.Models.Entities;

import org.app.Models.DAO.Admin.RouteDAO;
import org.app.Models.DAO.Admin.TicketDAO;
import org.app.Models.Helpers.LevenshteinDistance;
import org.views.admin.route.RouteView;

import java.awt.geom.RoundRectangle2D;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Route {

    private static final RouteView routeView = new RouteView();
    private static final RouteDAO routeDAO = new RouteDAO();
    private static final Scanner scanner = new Scanner(System.in);

    private int id;
    private String departureCity;
    private String destinationCity;
    private LocalDateTime departureDate;
    private BigDecimal price;
    private UUID partnerId;

    public Route(int id, String departureCity, String destinationCity, LocalDateTime departureDate, BigDecimal price, UUID partnerId) {
        this.id = id;
        this.departureCity = departureCity;
        this.destinationCity = destinationCity;
        this.departureDate = departureDate;
        this.price = price;
        this.partnerId = partnerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public UUID getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(UUID partnerId) {
        this.partnerId = partnerId;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", departureCity='" + departureCity + '\'' +
                ", destinationCity='" + destinationCity + '\'' +
                ", departureDate='" + departureDate + '\'' +
                ", price=" + price +
                ", partnerId=" + partnerId +
                '}';
    }

    public static void getRouteById() throws SQLException {
        int routeID = routeView.getRouteId();
        Route route = routeDAO.findById(routeID);
        routeView.displayRoute(route);
    }

    public static void getAllRoutes() throws SQLException {
        List<Route> routes = routeDAO.getAllRoutes();
        routeView.displayRoutesList(routes);
    }

    public static void addRoute() throws SQLException {
        Route route = routeView.addRoute();
        routeDAO.save(route);
    }

    public static void updateRoute() {
        routeView.updateRoute();
    }

    public static void deleteRoute() throws SQLException {
        System.out.print("Enter Route ID to delete: ");
        int deleteRouteId = scanner.nextInt();
        RouteDAO routeDAO1 = new RouteDAO();
        Route routeToDelete = routeDAO1.findById(deleteRouteId);
        if (routeToDelete != null && LevenshteinDistance.confirmDeletion()) {
            routeDAO1.delete(deleteRouteId);
            System.out.println("Route successfully deleted.");
        }
    }
    public static void restoreRoute() throws SQLException {
        System.out.print("Enter Route ID to restore: ");
        int restoreRouteId = scanner.nextInt();
        routeDAO.restore(restoreRouteId);
        System.out.println("Route successfully restored.");
    }

}
