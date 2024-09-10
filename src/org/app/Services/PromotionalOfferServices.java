package org.app.Services;

import org.app.Models.Entities.PromotionalOffer;
import org.app.Models.DAO.Admin.PromotionalOfferDAO;
import org.app.Models.Entities.Ticket;
import org.views.admin.promotonalOffer.PromotionalOfferView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class PromotionalOfferServices {
    private final PromotionalOfferDAO promotionalOfferDAO;
    private final PromotionalOfferView promotionalOfferView;
    Scanner scanner = new Scanner(System.in);

    public PromotionalOfferServices() {
        this.promotionalOfferDAO = new PromotionalOfferDAO();
        this.promotionalOfferView = new PromotionalOfferView();
    }

    public void managePromotionalOffer() throws SQLException {
        boolean continueManaging = true;

        while (continueManaging) {
            int action = promotionalOfferView.operationType();

            switch (action) {
                case 1:
                    PromotionalOffer.getPromotionalOfferByID();
                    break;
                case 2:
                    PromotionalOffer.getAllPromotionalOffers();
                    break;
                case 3:
                    PromotionalOffer.addPromotionalOffer();
                    break;
                case 4:
                    PromotionalOffer.updatePromotionalOffer();
                    break;
                case 5:
                    PromotionalOffer.deletePromotionalOffer();
                    break;
                case 6:
                    PromotionalOffer.restorePromotionalOffer();
                case 0:
                    continueManaging = false;
                    System.out.println("Exiting admin management.");
                    break;
                default:
                    System.out.println("Invalid option. Please choose a valid action.");
                    break;
            }
        }
    }



}
