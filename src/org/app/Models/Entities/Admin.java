
package org.app.Models.Entities;

import org.app.Models.DAO.Admin.AdminDAO;
import org.app.Models.Enums.Role;
import org.views.admin.person.AdminView;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.app.Models.Helpers.LevenshteinDistance;

public class Admin extends Person {
    private static AdminView adminView = new AdminView();
    private static AdminDAO adminDAO = new AdminDAO();

    private static Scanner scanner = new Scanner(System.in);

    public Admin(int id, String firstName, String lastName, String email, String phone, Role role, String hashedPassword, LocalDateTime createdAt) {
        super(id, firstName, lastName, email, phone, role, hashedPassword,createdAt);
    }



    @Override
    public String toString() {
        return "Admin{" +
                ", Person=" + super.toString() +
                '}';
    }

    public static void getAdminById() throws SQLException {
        int id = adminView.getAdmin();
        Admin admin = adminDAO.findById(id);
        adminView.displayAdmin(admin);
    }

    public static void getAllAdmins() throws SQLException {
        ArrayList<Admin> admins = adminDAO.getAllAdmin();
        adminView.displayAdminsList(admins);
    }

    public static void addAdmin() throws SQLException{
        Admin newAdmin = adminView.addAdmin();
        adminDAO.save(newAdmin); // Implement this method to add an admin
    }

    public static void updateAdmin() throws SQLException {
        Admin updateAdmin = adminView.addAdmin();
        adminDAO.update(updateAdmin); // Implement this method to edit an admin
    }

    public static void deleteAdmin() throws SQLException {
        int deletedAdminId = adminView.getAdmin();
        if(LevenshteinDistance.confirmDeletion()) {
            adminDAO.delete(deletedAdminId); // Implement this method to delete an admin
        }
    }

    public static void restoreAdmin() throws SQLException {
        int restoredAdminId = adminView.getAdmin();
        adminDAO.restore(restoredAdminId); // Implement this method to restore an admin
    }


}
