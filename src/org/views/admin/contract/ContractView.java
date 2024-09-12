package org.views.admin.contract;

import org.app.Models.Entities.Contract;
import org.app.Models.Entities.PromotionalOffer;
import org.app.Models.Enums.CurrentStatus;
import org.app.Models.DAO.Admin.PromotionalOfferDAO;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ContractView {

    private static final Scanner scanner = new Scanner(System.in);

    public int operationType() {
        System.out.println("\n=====================================================================================================================================");
        System.out.println("                                             Welcome to the Contract Entity Management                                         ");
        System.out.println("=====================================================================================================================================");
        System.out.println("Choose an operation:");
        System.out.println("1. List a Single Contract");
        System.out.println("2. List All Contracts");
        System.out.println("3. Add a New Contract");
        System.out.println("4. Update an Existing Contract");
        System.out.println("5. Delete a Contract");
        System.out.println("6. Restore a Deleted Contract");
        System.out.println("0. Exit");
        System.out.println("\nEnter your choice (0-6): ");
        return scanner.nextInt();
    }

    public UUID getContract() {
        System.out.println("\nPlease enter the Contract ID (UUID format):");
        return UUID.fromString(scanner.next());
    }

    public void displayContract(Contract contract) {
        System.out.println("\n=====================================================================================================================================================================================================");
        System.out.println("|              Contract ID            |    Initial Date   |    End Date   |      Special Tariff     |     Accord Conditions     |  Renewed  |    Status   |           Partner ID              |");
        System.out.println("=======================================================================================================================================================================================================");
        String renewed = contract.isRenewed() ? "Yes" : "No";
        System.out.printf("| %21s | %16s | %13s | %18s | %24s | %8s | %7s | %24s |\n",
                contract.getId(), contract.getInitDate(), contract.getEndDate(),
                contract.getSpecialTariff(), contract.getAccordConditions(),
                renewed, contract.getCurrentStatus(), contract.getPartner_id());
        System.out.println("=========================================================================================================================================================");
    }

    public void displayContractsList(List<Contract> contracts) {
        System.out.println("\n=====================================================================================================================================================================================================");
        System.out.println("|              Contract ID            |    Initial Date   |    End Date   |      Special Tariff     |     Accord Conditions     |  Renewed  |    Status   |           Partner ID              |");
        System.out.println("=======================================================================================================================================================================================================");
        contracts.stream()
                .forEach(contract -> {
                    String renewed = contract.isRenewed() ? "Yes" : "No";
                    System.out.printf("| %21s | %16s | %13s | %18s | %24s | %8s | %7s | %24s |\n",
                            contract.getId(), contract.getInitDate(), contract.getEndDate(),
                            contract.getSpecialTariff(), contract.getAccordConditions(),
                            renewed, contract.getCurrentStatus(), contract.getPartner_id());
                });
        System.out.println("=======================================================================================================================================================================================================");
    }

    public static Contract addContract() throws SQLException {
        System.out.println("\n==========================================");
        System.out.println("           Add a New Contract             ");
        System.out.println("==========================================\n");

        UUID contractId = null;
        while (contractId == null) {
            System.out.println("Please enter a valid Contract ID (UUID format):");
            try {
                contractId = UUID.fromString(scanner.next());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid format. Example: '550e8400-e29b-41d4-a716-446655440000'");
            }
        }

        Date initDate = null, endDate = null;
        while (initDate == null || endDate == null) {
            try {
                System.out.println("Enter Contract Initial Date (YYYY-MM-DD): ");
                initDate = Date.valueOf(scanner.next());
                System.out.println("Enter Contract End Date (YYYY-MM-DD): ");
                endDate = Date.valueOf(scanner.next());

                if (!initDate.before(endDate)) {
                    System.out.println("Error: Initial date must be before the End date.");
                    initDate = null;
                    endDate = null;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }

        System.out.print("Enter Special Tariff (e.g., 100.50): ");
        BigDecimal specialTariff = scanner.nextBigDecimal();
        scanner.nextLine(); // Consume newline

        String accordConditions;
        do {
            System.out.print("Enter Contract Accord Conditions (min 20 characters): ");
            accordConditions = scanner.nextLine();
        } while (accordConditions.length() < 20);

        System.out.print("Is the contract renewed? (true/false): ");
        boolean renewed = scanner.nextBoolean();

        System.out.print("Enter Contract Partner ID (UUID format): ");
        UUID partnerID = UUID.fromString(scanner.next());

        // Adding Promotional Offers
        List<PromotionalOffer> promotionalOffers = new ArrayList<>();
        UUID offerID = null;
        while (offerID == null) {
            System.out.print("Enter Promotional Offer ID (or type 'done' to finish): ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("done")) break;

            try {
                offerID = UUID.fromString(input);
                PromotionalOffer promotionalOffer = PromotionalOfferDAO.findById(offerID);
                if (promotionalOffer != null) {
                    promotionalOffers.add(promotionalOffer);
                } else {
                    System.out.println("No Promotional Offer found with this ID.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid UUID format. Please try again.");
            }
        }

        return new Contract(contractId, initDate, endDate, specialTariff, accordConditions, renewed, CurrentStatus.IN_PROGRESS, partnerID, promotionalOffers);
    }
}
