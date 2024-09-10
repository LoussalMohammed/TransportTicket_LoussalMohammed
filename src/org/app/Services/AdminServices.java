package org.app.Services;

import org.app.Models.Entities.Admin;
import org.app.Models.DAO.Admin.AdminDAO;
import org.views.admin.person.AdminView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class AdminServices {

    private final AdminDAO adminDAO;
    private final AdminView adminView;

    public AdminServices() {
        this.adminDAO = new AdminDAO();
        this.adminView = new AdminView();
    }

    Scanner scanner = new Scanner(System.in);
    public void manageAdmins() throws SQLException {
        boolean continueManaging = true;

            while (continueManaging) {
                int action = adminView.operationType();

                switch (action) {
                    case 1:
                        Admin.getAdminById();
                        break;
                    case 2:
                        Admin.getAllAdmins();
                        break;
                    case 3:
                        Admin.addAdmin();
                        break;
                    case 4:
                        Admin.updateAdmin();
                        break;
                    case 5:
                        Admin.deleteAdmin();
                        break;
                    case 6:
                        Admin.restoreAdmin();
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






