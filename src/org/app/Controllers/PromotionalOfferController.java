package org.app.Controllers;

import org.app.Models.Entities.Admin;
import org.app.Models.Entities.PromotionalOffer;
import org.app.Services.Admin.AdminServices;
import org.app.Services.Admin.PromotionalOfferServices;
import org.views.admin.person.AdminView;
import org.views.admin.promotonalOffer.PromotionalOfferView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class PromotionalOfferController {
    private final PromotionalOfferServices promotionalOfferServices;
    private final PromotionalOfferView promotionalOfferView;
    Scanner scanner = new Scanner(System.in);

    public PromotionalOfferController() {
        this.promotionalOfferServices = new PromotionalOfferServices();
        this.promotionalOfferView = new PromotionalOfferView();
    }

    public void managePromotionalOffer() throws SQLException {
        boolean continueManaging = true;

        while (continueManaging) {
            int action = promotionalOfferView.operationType();

            switch (action) {
                case 1:
                    UUID id = promotionalOfferView.getOfferId();
                    PromotionalOffer promotionalOffer = promotionalOfferServices.findById(id);
                    promotionalOfferView.displayPromotionalOffer(promotionalOffer);
                    break;
                case 2:
                    ArrayList<PromotionalOffer> promotionalOffers = (ArrayList<PromotionalOffer>) promotionalOfferServices.getPromotionalOffers();
                    promotionalOfferView.displayPromotionalOffersList(promotionalOffers);
                    break;
                case 3:
                    PromotionalOffer newPromotionalOffer = promotionalOfferView.addOffer();
                    promotionalOfferServices.save(newPromotionalOffer); // Implement this method to add an admin
                    break;
                case 4:
                    UUID offerId = promotionalOfferView.getOfferId();
                    PromotionalOffer updatedPromotionalOffer = promotionalOfferServices.findById(offerId);
                    PromotionalOffer updatePromotionalOffer = promotionalOfferView.updateOffer(updatedPromotionalOffer);
                    promotionalOfferServices.update(updatePromotionalOffer); // Implement this method to edit an admin
                    break;
                case 5:
                    UUID deletedAdminId = promotionalOfferView.getOfferId();
                    promotionalOfferServices.delete(deletedAdminId); // Implement this method to delete an admin
                    break;
                case 6:
                    UUID restoredAdminId = promotionalOfferView.getOfferId();
                    promotionalOfferServices.restore(restoredAdminId);
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
