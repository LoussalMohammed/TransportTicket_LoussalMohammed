package org.app.Controllers;

import org.app.Models.Entities.Contract;
import org.app.Services.Admin.ContractServices;
import org.views.admin.contract.ContractView;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ContractController {

    private final ContractServices contractServices;
    private final ContractView contractView;
    private final Scanner scanner = new Scanner(System.in);

    // Constructor
    public ContractController() {
        this.contractServices = new ContractServices();
        this.contractView = new ContractView();
    }

    // Method to manage contract operations
    public void manageContract() throws SQLException {
        boolean continueManaging = true;

        while (continueManaging) {
            int action = contractView.operationType();

            switch (action) {
                case 1:
                    // Display a single contract
                    UUID contractId = contractView.getContract();
                    Contract contract = contractServices.findById(contractId);
                    if (contract != null) {
                        contractView.displayContract(contract);
                    } else {
                        System.out.println("Contract not found.");
                    }
                    break;

                case 2:
                    // Display all contracts
                    List<Contract> contracts = contractServices.getContracts();
                    if (contracts.isEmpty()) {
                        System.out.println("No contracts found.");
                    } else {
                        contractView.displayContractsList(contracts);
                    }
                    break;

                case 3:
                    // Add a new contract
                    Contract newContract = contractView.addContract();
                    if (newContract != null) {
                        contractServices.save(newContract);
                        System.out.println("New contract successfully added.");
                    } else {
                        System.out.println("Failed to add contract.");
                    }
                    break;

                case 4:
                    // Update an existing contract
                    UUID updateContractId = contractView.getContract();
                    Contract existingContract = contractServices.findById(updateContractId);
                    if (existingContract != null) {
                        System.out.println("Updating contract details...");
                        Contract updatedContractData = contractView.addContract(); // Should update only the desired fields.
                        updatedContractData.setId(existingContract.getId()); // Ensure ID remains the same.
                        contractServices.update(updatedContractData);
                        System.out.println("Contract successfully updated.");
                    } else {
                        System.out.println("Contract not found.");
                    }
                    break;

                case 5:
                    // Delete a contract
                    UUID deleteContractId = contractView.getContract();
                    Contract contractToDelete = contractServices.findById(deleteContractId);
                    if (contractToDelete != null) {
                        contractServices.delete(deleteContractId);
                        System.out.println("Contract successfully deleted.");
                    } else {
                        System.out.println("Contract not found.");
                    }
                    break;

                case 6:
                    // Restore a deleted contract
                    UUID restoreContractId = contractView.getContract();
                    contractServices.restore(restoreContractId);
                    System.out.println("Contract successfully restored.");

                    break;

                case 0:
                    // Exit contract management
                    continueManaging = false;
                    System.out.println("Exiting contract management.");
                    break;

                default:
                    // Invalid action handling
                    System.out.println("Invalid option. Please choose a valid action.");
                    break;
            }
        }
    }
}
