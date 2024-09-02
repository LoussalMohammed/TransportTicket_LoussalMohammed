package app.tools;

import java.sql.SQLException;
import java.sql.Connection;

public class DatabaseConnection {


    public static void main(String[] args) {

        databaseC db = null;

        try {
            db = databaseC.getInstance();
            Connection conn = db.getConnection();

            System.out.println("database connected....");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (db != null) {
                db.closeConnection();
            }
        }


    }


}