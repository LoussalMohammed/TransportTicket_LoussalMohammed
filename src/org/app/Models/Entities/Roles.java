package org.app.Models.Entities;

import java.util.ArrayList;
import java.util.Objects;

public class Roles {
    private static final String USER = "user";
    private static final String ADMIN = "admin";

    public static String getRoleUser() {
        return USER;
    }

    public static String getRoleAdmin() {
        return ADMIN;
    }




}