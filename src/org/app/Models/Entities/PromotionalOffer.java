package org.app.Models.Entities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.app.Models.DAO.Admin.PromotionalOfferDAO;
import org.app.Models.Enums.ReductionType; // Assuming the enum is in the tools package
import org.app.Models.Enums.OfferStatus;   // Assuming the enum is in the tools package
import org.app.Models.Helpers.LevenshteinDistance;
import org.views.admin.promotonalOffer.PromotionalOfferView;

public class PromotionalOffer {

    private static PromotionalOfferDAO promotionalOfferDAO = new PromotionalOfferDAO();

    private static PromotionalOfferView promotionalOfferView = new PromotionalOfferView();
    private UUID id;
    private String name;
    private String description;
    private Date initDate;
    private Date endDate;
    private ReductionType reductionType;
    private Float reductionValue;
    private String condition;
    private OfferStatus offerStatus;


    // Constructor
    public PromotionalOffer(UUID id, String name, String description,
                            Date initDate, Date endDate,
                            ReductionType reductionType, Float reductionValue,
                            String condition, OfferStatus offerStatus) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.initDate = initDate;
        this.endDate = endDate;
        this.reductionType = reductionType;
        this.reductionValue = reductionValue;
        this.condition = condition;
        this.offerStatus = offerStatus;
    }

    // Getters and Setters
    public UUID

    getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getInitDate() {
        return initDate;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public ReductionType getReductionType() {
        return reductionType;
    }

    public void setReductionType(ReductionType reductionType) {
        this.reductionType = reductionType;
    }

    public Float getReductionValue() {
        return reductionValue;
    }

    public void setReductionValue(Float reductionValue) {
        this.reductionValue = reductionValue;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public OfferStatus getOfferStatus() {
        return offerStatus;
    }
    public void setOfferStatus(OfferStatus offerStatus) {
        this.offerStatus = offerStatus;
    }




    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", Name='" + name + '\'' +
                ", Description='" + description + '\'' +
                ", Init Date='" + initDate + '\'' +
                ", End Date='" + endDate + '\'' +
                ", Reduction Type=" + reductionType +
                ", Reduction Value=" + reductionValue +
                ", Condition=" + condition +
                ", Offer Status=" + offerStatus +
                '}';
    }

    public static void getPromotionalOfferByID() throws SQLException {
        UUID id = promotionalOfferView.getOfferId();
        PromotionalOffer promotionalOffer = promotionalOfferDAO.findById(id);
        promotionalOfferView.displayPromotionalOffer(promotionalOffer);
    }
    public static void getAllPromotionalOffers() throws SQLException {
        ArrayList<PromotionalOffer> promotionalOffers = (ArrayList<PromotionalOffer>) promotionalOfferDAO.getPromotionalOffers();
        promotionalOfferView.displayPromotionalOffersList(promotionalOffers);
    }
    public static void addPromotionalOffer() throws SQLException {
        PromotionalOffer newPromotionalOffer = promotionalOfferView.addOffer();
        promotionalOfferDAO.save(newPromotionalOffer); // Implement this method to add an admin
    }
    public static void updatePromotionalOffer() throws SQLException {
        UUID offerId = promotionalOfferView.getOfferId();
        PromotionalOffer updatedPromotionalOffer = promotionalOfferDAO.findById(offerId);
        PromotionalOffer updatePromotionalOffer = promotionalOfferView.updateOffer(updatedPromotionalOffer);
        promotionalOfferDAO.update(updatePromotionalOffer); // Implement this method to edit an admin
    }
    public static void deletePromotionalOffer() throws SQLException {
        UUID deletedAdminId = promotionalOfferView.getOfferId();
        if(LevenshteinDistance.confirmDeletion()) {
            promotionalOfferDAO.delete(deletedAdminId);
        }
    }
    public static void restorePromotionalOffer() throws SQLException {
        UUID restoredAdminId = promotionalOfferView.getOfferId();
        promotionalOfferDAO.restore(restoredAdminId);
    }
}