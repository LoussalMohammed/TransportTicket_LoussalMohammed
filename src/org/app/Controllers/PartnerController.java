package org.app.Controllers;

import org.app.Models.Entities.Partner;
import org.app.Services.Admin.PartenerServices;
import org.views.admin.partner.PartnerView;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class PartnerController {

    private final PartenerServices partnerServices;
    private final PartnerView partnerView;

    // Constructor
    public PartnerController() {
        this.partnerServices = new PartenerServices();
        this.partnerView = new PartnerView();
    }

    // Method to manage partner operations
    public void managePartner() throws SQLException {
        boolean continueManaging = true;

        while (continueManaging) {
            int action = partnerView.operationType(); // Get operation type from view

            switch (action) {
                case 1:
                    displaySinglePartner();
                    break;

                case 2:
                    displayAllPartners();
                    break;

                case 3:
                    addNewPartner();
                    break;

                case 4:
                    updateExistingPartner();
                    break;

                case 5:
                    deletePartner();
                    break;

                case 6:
                    restoreDeletedPartner();
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

    private void displaySinglePartner() throws SQLException {
        UUID partnerId = partnerView.getPartnerId(); // Fetch partner ID from view
        Partner partner = partnerServices.findById(partnerId);
        if (partner != null) {
            partnerView.displayPartner(partner); // Display partner details
        } else {
            System.out.println("Partner not found.");
        }
    }

    private void displayAllPartners() throws SQLException {
        List<Partner> partners = partnerServices.getPartners();
        if (partners.isEmpty()) {
            System.out.println("No partners found.");
        } else {
            partnerView.displayPartnersList(partners); // Display list of partners
        }
    }

    private void addNewPartner() throws SQLException {
        Partner newPartner = partnerView.addPartner(); // Get new partner details from view
        if (newPartner != null) {
            partnerServices.save(newPartner);
            System.out.println("New partner successfully added.");
        } else {
            System.out.println("Failed to add partner.");
        }
    }

    private void updateExistingPartner() throws SQLException {
        UUID updatePartnerId = partnerView.getPartnerId(); // Get partner ID for update
        Partner existingPartner = partnerServices.findById(updatePartnerId);
        if (existingPartner != null) {
            System.out.println("Updating partner details...");
            Partner updatedPartnerData = partnerView.addPartner(); // Get updated details
            updatedPartnerData.setId(existingPartner.getId()); // Ensure ID remains the same
            partnerServices.update(updatedPartnerData);
            System.out.println("Partner successfully updated.");
        } else {
            System.out.println("Partner not found.");
        }
    }

    private void deletePartner() throws SQLException {
        UUID deletePartnerId = partnerView.getPartnerId(); // Get partner ID for deletion
        Partner partnerToDelete = partnerServices.findById(deletePartnerId);
        if (partnerToDelete != null) {
            partnerServices.delete(deletePartnerId);
            System.out.println("Partner successfully deleted.");
        } else {
            System.out.println("Partner not found.");
        }
    }

    private void restoreDeletedPartner() throws SQLException {
        UUID restorePartnerId = partnerView.getPartnerId(); // Get partner ID for restoration
        partnerServices.restore(restorePartnerId);
        System.out.println("Partner successfully restored.");
    }
}
