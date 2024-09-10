package org.app.Models.Entities;

import org.app.Models.DAO.Admin.ContractDAO;
import org.app.Models.Enums.CurrentStatus;
import org.views.admin.contract.ContractView;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class Contract {

    private static ContractDAO contractDAO = new ContractDAO();
    private static ContractView contractView = new ContractView();
    private UUID id;
    private Date initDate;
    private Date endDate;
    private BigDecimal specialTariff;
    private String accordConditions;
    private boolean renewed;
    private CurrentStatus currentStatus;

    private UUID partner_id;

    private List<PromotionalOffer> promotionalOffers;

    public Contract(UUID id, Date initDate, Date endDate, BigDecimal specialTariff, String accordConditions, boolean renewed, CurrentStatus currentStatus, UUID partner_id, List<PromotionalOffer> promotionalOffers) {
        this.id = id;
        this.initDate = Date.valueOf(initDate.toLocalDate());
        this.endDate = Date.valueOf(endDate.toLocalDate());
        this.specialTariff = specialTariff;
        this.accordConditions = accordConditions;
        this.renewed = renewed;
        this.currentStatus = currentStatus;
        this.partner_id = partner_id;
        this.promotionalOffers = promotionalOffers;
    }
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public BigDecimal getSpecialTariff() {
        return specialTariff;
    }

    public void setSpecialTariff(BigDecimal specialTarif) {
        this.specialTariff = specialTarif;
    }

    public String getAccordConditions() {
        return accordConditions;
    }

    public void setAccordConditions(String accordConditions) {
        this.accordConditions = accordConditions;
    }

    public boolean isRenewed() {
        return renewed;
    }

    public void setRenewed(boolean renewed) {
        this.renewed = renewed;
    }

    public CurrentStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(CurrentStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public UUID getPartner_id() {
        return this.partner_id;
    }

    public void setPartner_id(UUID partner_id) {
        this.partner_id = partner_id;
    }

    public List<PromotionalOffer> getPromotionalOffers() {
        return this.promotionalOffers;
    }

    public void addPromotionalOffer(PromotionalOffer promotionalOffer) {
        this.promotionalOffers.add(promotionalOffer);
    }
    public void addPromotionalOffersList(List<PromotionalOffer> promotionalOffersList) {
        for(int i = 0; i < promotionalOffersList.size(); i++) {
            this.promotionalOffers.add(promotionalOffersList.get(i));
        }
    }

    public void setPromotionalOffers(List<PromotionalOffer> promotionalOffers) {
        this.promotionalOffers = promotionalOffers;
    }


    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", Init Date='" + initDate + '\'' +
                ", End Date='" + endDate + '\'' +
                ", Special Tariff ='" + specialTariff + '\'' +
                ", Accord Conditions='" + accordConditions + '\'' +
                ", renewed=" + renewed +
                ", Current Status=" + currentStatus +
                '}';
    }


    public static void getContractById() throws SQLException {
        UUID contractId = contractView.getContract();
        Contract contract = contractDAO.findById(contractId);
        if (contract != null) {
            contractView.displayContract(contract);
        } else {
            System.out.println("Contract not found.");
        }
    }

    public static void getAllContracts() throws SQLException {
        // Display all contracts
        List<Contract> contracts = contractDAO.getContracts();
        if (contracts.isEmpty()) {
            System.out.println("No contracts found.");
        } else {
            contractView.displayContractsList(contracts);
        }
    }

    public static void addContract() throws SQLException {
        Contract newContract = ContractView.addContract();
        if (newContract != null) {
            contractDAO.save(newContract);
            System.out.println("New contract successfully added.");
        } else {
            System.out.println("Failed to add contract.");
        }
    }

    public static void updateContract() throws SQLException {
        UUID updateContractId = contractView.getContract();
        Contract existingContract = contractDAO.findById(updateContractId);
        if (existingContract != null) {
            System.out.println("Updating contract details...");
            Contract updatedContractData = contractView.addContract(); // Should update only the desired fields.
            updatedContractData.setId(existingContract.getId()); // Ensure ID remains the same.
            contractDAO.update(updatedContractData);
            System.out.println("Contract successfully updated.");
        } else {
            System.out.println("Contract not found.");
        }
    }

    public static void deleteContract() throws  SQLException {
        UUID deleteContractId = contractView.getContract();
        Contract contractToDelete = contractDAO.findById(deleteContractId);
        if (contractToDelete != null) {
            contractDAO.delete(deleteContractId);
            System.out.println("Contract successfully deleted.");
        } else {
            System.out.println("Contract not found.");
        }
    }

    public static void restoreContact() throws SQLException {
        UUID restoreContractId = contractView.getContract();
        contractDAO.restore(restoreContractId);
        System.out.println("Contract successfully restored.");
    }
}
