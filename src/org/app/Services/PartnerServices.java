package org.app.Services;

import org.app.Models.DAO.Admin.PartnerDAO;
import org.app.Models.Entities.Partner;
import org.views.admin.partner.PartnerView;

import java.sql.SQLException;

public class PartnerServices {

    private final PartnerDAO partnerDAO;
    private final PartnerView partnerView;

    // Constructor
    public PartnerServices() {
        this.partnerDAO = new PartnerDAO();
        this.partnerView = new PartnerView();
    }

    // Method to manage partner operations
    public void managePartner() throws SQLException {
        boolean continueManaging = true;

        while (continueManaging) {
            int action = partnerView.operationType(); // Get operation type from view

            switch (action) {
                case 1:
                    Partner.displaySinglePartner();
                    break;

                case 2:
                    Partner.displayAllPartners();
                    break;

                case 3:
                    Partner.addNewPartner();
                    break;

                case 4:
                    Partner.updateExistingPartner();
                    break;

                case 5:
                    Partner.deletePartner();
                    break;

                case 6:
                    Partner.restoreDeletedPartner();
                    break;

                case 0:
                    continueManaging = false; // Exit the loop
                    System.out.println("Exiting partner management.");
                    break;

                default:
                    System.out.println("Invalid option. Please choose a valid action.");
                    break;
            }
        }
    }


}
