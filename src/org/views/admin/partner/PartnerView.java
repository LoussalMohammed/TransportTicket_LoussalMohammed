package org.views.admin.partner;

import org.app.Models.DAO.Admin.PartnerDAO;
import org.app.Models.Entities.Contract;
import org.app.Models.Entities.Partner;
import org.app.Models.Enums.PartenaryStatus;
import org.app.Models.Enums.Transport;
import org.views.admin.contract.ContractView;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class PartnerView {

    private final Scanner scanner = new Scanner(System.in);
    private static PartnerDAO partnerDAO = new PartnerDAO();

    public int operationType() {
        System.out.println("\n=====================================================================================================================================");
        System.out.println("                                             Welcome to the Partner Entity Management                                          ");
        System.out.println("=====================================================================================================================================");
        System.out.println("Choose an operation:");
        System.out.println("1. List a Single Partner");
        System.out.println("2. List All Partners");
        System.out.println("3. Add a New Partner");
        System.out.println("4. Update an Existing Partner");
        System.out.println("5. Delete a Partner");
        System.out.println("6. Restore a Partner");
        System.out.println("0. Exit");
        System.out.println("\nEnter your choice (0-5): ");
        return scanner.nextInt();
    }

    public UUID getPartnerId() {
        System.out.println("\nPlease enter the Partner ID (UUID format):");
        return UUID.fromString(scanner.next());
    }

    public void displayPartner(Partner partner) {
        System.out.println("\n=====================================================================================================================================");
        System.out.println("|        Partner ID       |     Company Name     |    Commercial Contact    |     Transport Type     |   Status  |    Created At   |");
        System.out.println("=====================================================================================================================================");
        System.out.printf("| %21s | %20s | %22s | %21s | %8s | %16s |\n",
                partner.getId(), partner.getCompanyName(), partner.getCommercialContact(),
                partner.getTransport(), partner.getPartenaryStatus(), partner.getCreatedAt());
        System.out.println("=====================================================================================================================================\n");
    }

    public void displayPartnersList(List<Partner> partners) {
        System.out.println("\n=====================================================================================================================================");
        System.out.println("|        Partner ID       |     Company Name     |    Commercial Contact    |     Transport Type     |   Status  |    Created At   |");
        System.out.println("=====================================================================================================================================");
        for (Partner partner : partners) {
            System.out.printf("| %21s | %20s | %22s | %21s | %8s | %16s |\n",
                    partner.getId(), partner.getCompanyName(), partner.getCommercialContact(),
                    partner.getTransport(), partner.getPartenaryStatus(), partner.getCreatedAt());
        }
        System.out.println("=====================================================================================================================================\n");
    }

    public List<Map> addPartner() throws SQLException {
        System.out.println("\n==========================================================");
        System.out.println("                    Add a New Partner                   ");
        System.out.println("==========================================================\n");

        System.out.println("Please enter a valid Partner ID (UUID format):");
        UUID partnerId = UUID.fromString(scanner.next());
        scanner.nextLine();
        System.out.print("Enter Company Name: ");
        String companyName = scanner.nextLine(); // Use nextLine for full input



        System.out.print("Enter Commercial Contact: ");
        String commercialContact = scanner.nextLine(); // Use nextLine for full input

        Transport transport = null;
        while (transport == null) {
            try {
                System.out.print("Select Transport Type BUS => 1, Train => 2, Airplane => 3: ");
                int Tt = Integer.parseInt(scanner.next());
                if (Tt == 1) {
                    transport = Transport.BUS;
                } else if (Tt == 2) {
                    transport = Transport.TRAIN;
                } else if (Tt == 3) {
                    transport = Transport.AIRPLANE;
                } else {
                    System.out.println("Invalid input. Please select a valid transport type.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number (1, 2, or 3).");
            }
        }

        scanner.nextLine();

        System.out.print("Enter Geographic Zone: ");
        String geographicZone = scanner.nextLine();

        System.out.print("Enter Special Condition: ");
        String specialCondition = scanner.nextLine();

        PartenaryStatus partenaryStatus = null;
        while (partenaryStatus == null) {
            try {
                System.out.print("Select Partenary Status, ACTIVE  => 1, INACTIVE => 2, SUSPENDED => 3: ");
                int ps = Integer.parseInt(scanner.next());
                if (ps == 1) {
                    partenaryStatus = PartenaryStatus.ACTIVE;
                } else if (ps == 2) {
                    partenaryStatus = PartenaryStatus.INACTIVE;
                } else if (ps == 3) {
                    partenaryStatus = PartenaryStatus.SUSPENDED;
                } else {
                    System.out.println("Invalid input. Please select a valid partenary status.");
                }
            } catch (NumberFormatException e) {
            }
        }



        System.out.println("here");
        LocalDateTime createdAt = LocalDateTime.now();


        Contract contract = ContractView.addContract();

        Partner partner = new Partner(partnerId, companyName, commercialContact, transport, geographicZone, specialCondition, partenaryStatus, createdAt, new ArrayList<>());

        HashMap<String, Contract> contractMap =  new HashMap<String, Contract>();
        contractMap.put("contract", contract);
        HashMap<String, Partner> partnerMap =  new HashMap<String, Partner>();
        partnerMap.put("partner", partner);

        List<Map> contract_partner = new ArrayList<>();
        contract_partner.add(contractMap);
        contract_partner.add(partnerMap);
            return contract_partner;
    }

    public Partner updatePartner(UUID partnerId) throws SQLException {
        System.out.println("\n==============================================================");
        System.out.println("                   Update an Existing Partner                   ");
        System.out.println("===============================================================\n");

        Partner existingPartner = partnerDAO.findById(partnerId);
        if (existingPartner == null) {
            System.out.println("Partner with ID " + partnerId + " not found.");
            return null; // Return if the partner doesn't exist
        }

        System.out.println("Updating details for Partner: " + existingPartner.getCompanyName());

        // Step 3: Update fields (skip fields you want to leave unchanged)
        System.out.print("Enter new Company Name (press Enter to skip): ");
        String companyName = scanner.nextLine();
        if (!companyName.isEmpty()) {
            existingPartner.setCompanyName(companyName);
        }

        System.out.print("Enter new Commercial Contact (press Enter to skip): ");
        String commercialContact = scanner.nextLine();
        if (!commercialContact.isEmpty()) {
            existingPartner.setCommercialContact(commercialContact);
        }

        Transport transport = existingPartner.getTransport(); // Get existing transport type
        System.out.print("Select Transport Type BUS => 1, Train => 2, Airplane => 3 (press Enter to skip): ");
        String transportInput = scanner.nextLine();
        if (!transportInput.isEmpty()) {
            int Tt = Integer.parseInt(transportInput);
            transport = Tt == 1 ? Transport.BUS : Tt == 2 ? Transport.TRAIN : Transport.AIRPLANE;
            existingPartner.setTransport(transport);
        }

        System.out.print("Enter new Geographic Zone (press Enter to skip): ");
        String geographicZone = scanner.nextLine();
        if (!geographicZone.isEmpty()) {
            existingPartner.setGeographicZone(geographicZone);
        }

        System.out.print("Enter new Special Condition (press Enter to skip): ");
        String specialCondition = scanner.nextLine();
        if (!specialCondition.isEmpty()) {
            existingPartner.setSpecialCondition(specialCondition);
        }

        PartenaryStatus partenaryStatus = existingPartner.getPartenaryStatus(); // Get existing status
        System.out.print("Select Partenary Status ACTIVE => 1, INACTIVE => 2, SUSPENDED => 3 (press Enter to skip): ");
        String partenaryStatusInput = scanner.nextLine();
        if (!partenaryStatusInput.isEmpty()) {
            int ps = Integer.parseInt(partenaryStatusInput);
            partenaryStatus = ps == 1 ? PartenaryStatus.ACTIVE : ps == 2 ? PartenaryStatus.INACTIVE : PartenaryStatus.SUSPENDED;
            existingPartner.setPartenaryStatus(partenaryStatus);
        }

        // Set updated date
        existingPartner.setCreatedAt(LocalDateTime.now());

        System.out.println("Partner updated successfully.");

        // Return the updated partner
        return existingPartner;
    }

}



