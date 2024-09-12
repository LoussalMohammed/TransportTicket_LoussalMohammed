package org.views.admin.promotonalOffer;

import org.app.Models.Entities.PromotionalOffer;
import org.app.Models.Enums.ReductionType;
import org.app.Models.Enums.OfferStatus;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;
import java.sql.Date;

public class PromotionalOfferView {

    private Scanner scanner = new Scanner(System.in);

    public int operationType() {
        System.out.println("\n==========================================================================================================================");
        System.out.println("                                             Welcome to the Promotional Offer Management                                  ");
        System.out.println("==========================================================================================================================");

        System.out.println("\n Choose Type Of Operation: ListOne => 1, ListAll => 2, Add => 3, Update => 4, Delete => 5, Restore => 6, To Get Out => 0:");
        Integer operation = scanner.nextInt();
        scanner.nextLine();
        return operation;
    }

    public UUID getOfferId() {
        System.out.println("\n Enter the ID of the Promotional Offer:");
        String offerIdStr = scanner.nextLine();
        return UUID.fromString(offerIdStr);
    }

    public void displayPromotionalOffer(PromotionalOffer offer) {
        System.out.println("\n==================================================================================================================================================================================");
        System.out.println("|                          |                                  |                              |                              |                              |");
        System.out.println("|            id            |               name               |            description        |          init Date          |           end Date          |");
        System.out.println("|                          |                                  |                              |                              |                              |");
        System.out.println("\n==================================================================================================================================================================================");

        System.out.println("\n======================================================================================================================================================================");
        System.out.println("|                          |                                  |                              |                              |                              |");
        System.out.println("|    "+ offer.getId() + "     |    "+ offer.getName() + "      |    "+ offer.getDescription() + "      |    "+ offer.getInitDate() + "      |    "+ offer.getEndDate() + "      |");
        System.out.println("|                          |                                  |                              |                              |                              |");
        System.out.println("\n======================================================================================================================================================================");
    }

    public void displayPromotionalOffersList(ArrayList<PromotionalOffer> offers) {
        System.out.println("\n======================================================================================================================================================================================");
        System.out.println("|                                   |                                    |                                     |                                    |                                   |");
        System.out.println("|                 id                |               name                   |             description              |              Init Date            |             End Date             |");
        System.out.println("|                                   |                                    |                                     |                                    |                                   |");
        System.out.println("\n======================================================================================================================================================================================");
        offers.stream()
                .forEach(offer -> {
                    System.out.println("\n======================================================================================================================================================================================");
                    System.out.println("|                                   |                                    |                                  |                                   |                                   |");
                    System.out.println("|      "+ offer.getId() +"            |      "+ offer.getName() +"      |      "+ offer.getDescription() +"        |       "+ offer.getInitDate() +"        |        "+ offer.getEndDate() +"        |");
                    System.out.println("|                                   |                                    |                                  |                                   |                                   |");
                    System.out.println("\n======================================================================================================================================================================================");
                });
    }

    public PromotionalOffer addOffer() {
        System.out.println("\n================================================================================");
        System.out.println("Enter Promotional Offer Details:\n");

        System.out.println("Enter Offer Name:\t");
        String name = scanner.nextLine();

        System.out.println("Enter Offer Description:\t");
        String description = scanner.nextLine();

        System.out.println("Enter Initial Date (yyyy-mm-dd):\t");
        Date initDate = Date.valueOf(scanner.nextLine());

        System.out.println("Enter End Date (yyyy-mm-dd):\t");
        Date endDate = Date.valueOf(scanner.nextLine());

        System.out.println("Enter Reduction Type, Percentage => 1, FixedPrice => 2: \t");
        int rt = scanner.nextInt();
        ReductionType reductionType = rt == 1 ? ReductionType.PERCENTAGE :  ReductionType.FIXED_PRICE;

        System.out.println("Enter Reduction Value:\t");
        Float reductionValue = scanner.nextFloat();
        scanner.nextLine();

        System.out.println("Enter Offer Condition:\t");
        String condition = scanner.nextLine();

        OfferStatus offerStatus = OfferStatus.ACTIVE; // Default status

        PromotionalOffer offer = new PromotionalOffer(UUID.randomUUID(), name, description, initDate, endDate, reductionType, reductionValue, condition, offerStatus);

        System.out.println("\n================================================================================");
        return offer;
    }

    public PromotionalOffer updateOffer(PromotionalOffer offer) {
        System.out.println("\nUpdating Promotional Offer: " + offer.getId());

        System.out.println("Current Name: " + offer.getName());
        System.out.println("Enter new Name (or press Enter to keep current): \t");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            offer.setName(name);
        }

        System.out.println("Current Description: " + offer.getDescription());
        System.out.println("Enter new Description (or press Enter to keep current): \t");
        String description = scanner.nextLine();
        if (!description.isEmpty()) {
            offer.setDescription(description);
        }

        System.out.println("Current Init Date: " + offer.getInitDate());
        System.out.println("Enter new Init Date (yyyy-mm-dd or press Enter to keep current): \t");
        String initDateInput = scanner.nextLine();
        if (!initDateInput.isEmpty()) {
            offer.setInitDate(Date.valueOf(initDateInput));
        }

        System.out.println("Current End Date: " + offer.getEndDate());
        System.out.println("Enter new End Date (yyyy-mm-dd or press Enter to keep current): \t");
        String endDateInput = scanner.nextLine();
        if (!endDateInput.isEmpty()) {
            offer.setEndDate(Date.valueOf(endDateInput));
        }
        System.out.println("Enter Reduction Type, Percentage => 1, FixedPrice => 2: \t");
        int rt = scanner.nextInt();
        ReductionType reductionType = rt == 1 ? ReductionType.PERCENTAGE :  ReductionType.FIXED_PRICE;
        offer.setReductionType(reductionType);

        System.out.println("Enter Reduction Value: \t");
        Float reductionValue = scanner.nextFloat();

        offer.setReductionValue(reductionValue);

        System.out.println("Enter Condition: \t");
        String condition = scanner.nextLine();

        offer.setCondition(condition);

        System.out.println("Enter Offer Status, Active => 1, INACTIVE => 2, SUSPENDED => 3: \t");
        int os = scanner.nextInt();
        OfferStatus offerStatus = os == 1 ? OfferStatus.ACTIVE : os == 2 ? OfferStatus.INACTIVE : OfferStatus.SUSPENDED;
        offer.setOfferStatus(offerStatus);



        System.out.println("Promotional Offer updated successfully!");

            return offer;
    }

}
