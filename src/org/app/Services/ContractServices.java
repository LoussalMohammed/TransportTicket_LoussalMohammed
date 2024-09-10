package org.app.Services;

import org.app.Models.Entities.Contract;
import org.app.Models.DAO.Admin.ContractDAO;
import org.views.admin.contract.ContractView;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ContractServices {

    private final ContractDAO contractDAO;
    private final ContractView contractView;
    private final Scanner scanner = new Scanner(System.in);

    // Constructor
    public ContractServices() {
        this.contractDAO = new ContractDAO();
        this.contractView = new ContractView();
    }

    // Method to manage contract operations
    public void manageContract() throws SQLException {
        boolean continueManaging = true;

        while (continueManaging) {
            int action = contractView.operationType();

            switch (action) {
                case 1:
                    Contract.getContractById();
                    break;

                case 2:
                    // Display all contracts
                    Contract.getAllContracts();
                    break;

                case 3:
                    // Add a new contract
                    Contract.addContract();
                    break;

                case 4:
                    // Update an existing contract
                    Contract.updateContract();
                    break;

                case 5:
                    // Delete a contract
                    Contract.deleteContract();
                    break;

                case 6:
                    // Restore a deleted contract
                    Contract.restoreContact();
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
