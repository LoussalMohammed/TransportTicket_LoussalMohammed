package org.views;

import org.app.Controllers.AuthController;
import org.app.Models.Entities.Admin;
import org.app.Models.Enums.Role;
import org.app.Services.Admin.AdminServices;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws SQLException {
        AuthController authController = new AuthController();
        authController.handleLogin();
    }
}
