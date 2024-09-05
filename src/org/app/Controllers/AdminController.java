package org.app.Controllers;

import org.app.Models.Entities.Admin;
import org.app.Services.Admin.AdminServices;
import org.views.admin.person.AdminView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class AdminController {

    private final AdminServices adminServices;
    private final AdminView adminView;

    public AdminController() {
        this.adminServices = new AdminServices();
        this.adminView = new AdminView();
    }

    Scanner scanner = new Scanner(System.in);
    public void manageAdmins() throws SQLException {
        boolean continueManaging = true;

            while (continueManaging) {
                int action = adminView.operationType();

                switch (action) {
                    case 1:
                        int id = adminView.getAdmin();
                        Admin admin = adminServices.findById(id);
                        adminView.displayAdmin(admin);
                        break;
                    case 2:
                        ArrayList<Admin> admins = adminServices.getAllAdmin();
                        adminView.displayAdminsList(admins);
                        break;
                    case 3:
                        Admin newAdmin = adminView.addAdmin();
                        adminServices.save(newAdmin); // Implement this method to add an admin
                        break;
                    case 4:
                        Admin updateAdmin = adminView.addAdmin();
                        adminServices.update(updateAdmin); // Implement this method to edit an admin
                        break;
                    case 5:
                        int deletedAdminId = adminView.getAdmin();
                        adminServices.delete(deletedAdminId); // Implement this method to delete an admin
                        break;
                    case 6:
                        int restoredAdminId = adminView.getAdmin();
                        adminServices.restore(restoredAdminId);
                    case 0:
                        continueManaging = false;
                        System.out.println("Exiting admin management.");
                        break;
                    default:
                        System.out.println("Invalid option. Please choose a valid action.");
                        break;
                }
            }
        }
    }




