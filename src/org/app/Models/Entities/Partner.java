package org.app.Models.Entities;

import org.app.Models.DAO.Admin.PartnerDAO;
import org.app.Models.Enums.PartenaryStatus;
import org.app.Models.Enums.Transport;
import org.views.admin.partner.PartnerView;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

public class Partner {

    private static PartnerDAO partnerDAO = new PartnerDAO();

    private static PartnerView partnerView = new PartnerView();
    private UUID id;
    private String companyName;
    private String commercialContact;
    private Transport transport;
    private String geographicZone;
    private String specialCondition;
    private PartenaryStatus partenaryStatus;
    private LocalDateTime createdAt;

    private ArrayList<Contract> contracts;

    // Constructor
    public Partner(UUID id, String companyName, String commercialContact,
                   Transport transport, String geographicZone,
                   String specialCondition, PartenaryStatus partenaryStatus,
                   LocalDateTime createdAt, ArrayList<Contract> contract) {
        this.id = id;
        this.companyName = companyName;
        this.commercialContact = commercialContact;
        this.transport = transport;
        this.geographicZone = geographicZone;
        this.specialCondition = specialCondition;
        this.partenaryStatus = partenaryStatus;
        this.createdAt = createdAt;
        this.contracts = contracts != null ? contracts : new ArrayList<>(10);
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCommercialContact() {
        return commercialContact;
    }

    public void setCommercialContact(String commercialContact) {
        this.commercialContact = commercialContact;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public String getGeographicZone() {
        return geographicZone;
    }

    public void setGeographicZone(String geographicZone) {
        this.geographicZone = geographicZone;
    }

    public String getSpecialCondition() {
        return specialCondition;
    }

    public void setSpecialCondition(String specialCondition) {
        this.specialCondition = specialCondition;
    }

    public PartenaryStatus getPartenaryStatus() {
        return partenaryStatus;
    }

    public void setPartenaryStatus(PartenaryStatus partenaryStatus) {
        this.partenaryStatus = partenaryStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void addContract(Contract contract) {
        this.contracts.add(contract);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", Company Name='" + companyName + '\'' +
                ", Commercial Contact='" + commercialContact + '\'' +
                ", Transport Type='" + transport + '\'' +
                ", Geographic Zone='" + geographicZone + '\'' +
                ", Special Condition=" + specialCondition +
                ", Partenary Status=" + partenaryStatus +
                ", Created At=" + createdAt +
                '}';
    }

    public static void displaySinglePartner() throws SQLException {
        UUID partnerId = partnerView.getPartnerId(); // Fetch partner ID from view
        Partner partner = partnerDAO.findById(partnerId);
        if (partner != null) {
            partnerView.displayPartner(partner); // Display partner details
        } else {
            System.out.println("Partner not found.");
        }
    }

    public static void displayAllPartners() throws SQLException {
        List<Partner> partners = partnerDAO.getPartners();
        if (partners.isEmpty()) {
            System.out.println("No partners found.");
        } else {
            partnerView.displayPartnersList(partners); // Display list of partners
        }
    }

    public static void addNewPartner() throws SQLException {
        try {
            List<Map> partner_contract = partnerView.addPartner(); // Get new partner details from view
            System.out.println("partner_contract");

            if (partner_contract != null) {

                Map<String, Contract> contractMap = (Map<String, Contract>) partner_contract.get(0);
                Map<String, Partner> partnerMap = (Map<String, Partner>) partner_contract.get(1);
                partnerDAO.save(partnerMap.get("partner"));
                System.out.println("New partner successfully added.");
                partnerDAO.saveContract(contractMap.get("contract"));
                System.out.println("New contract successfully added.");
            } else {
                System.out.println("Failed to add partner.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateExistingPartner() throws SQLException {
        UUID updatePartnerId = partnerView.getPartnerId(); // Get partner ID for update
        Partner existingPartner = partnerDAO.findById(updatePartnerId);
        if (existingPartner != null) {
            System.out.println("Updating partner details...");
            Partner updatedPartnerData = partnerView.updatePartner(updatePartnerId); // Get updated details
            updatedPartnerData.setId(existingPartner.getId()); // Ensure ID remains the same
            partnerDAO.update(updatedPartnerData);
            System.out.println("Partner successfully updated.");
        } else {
            System.out.println("Partner not found.");
        }
    }

    public static void deletePartner() throws SQLException {
        UUID deletePartnerId = partnerView.getPartnerId(); // Get partner ID for deletion
        Partner partnerToDelete = partnerDAO.findById(deletePartnerId);
        if (partnerToDelete != null) {
            partnerDAO.delete(deletePartnerId);
            System.out.println("Partner successfully deleted.");
        } else {
            System.out.println("Partner not found.");
        }
    }

    public static void restoreDeletedPartner() throws SQLException {
        UUID restorePartnerId = partnerView.getPartnerId(); // Get partner ID for restoration
        partnerDAO.restore(restorePartnerId);
        System.out.println("Partner successfully restored.");
    }

}
