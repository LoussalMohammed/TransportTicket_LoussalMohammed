import Data.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static Data.Data.url;

public class Database {


    public static void main(String[] args) {
        try {
            // Establishing connection to the database
            Connection connection = DriverManager.getConnection(url(), Data.user(), Data.password());
            System.out.println("Connected to DataBase...");

            // Creating a statement to execute SQL queries
            Statement statement = connection.createStatement();
            statement.execute(createTicketStatus());
            System.out.println("TYPE 'TickerStatus' created successfully!");
            statement.execute(createTicket());
            System.out.println("TABLE 'Ticket' created successfully!");
        } catch (SQLException e) {
            System.out.println("Could Not Connect: " + e.getMessage());
        }
    }

    /**
     * Generates a SQL query to drop a specified type or table if it exists.
     *
     * @param type The type of the SQL object (TABLE or TYPE).
     * @param name The name of the SQL object to drop.
     * @return A SQL query string to drop the specified object.
     */
    public static String drop(String type, String name) {
        return "DROP " + type + " IF EXISTS " + name + ";";
    }

    /**
     * Generates a SQL query to create a 'person' table.
     *
     * @return A SQL query string to create the 'person' table.
     */
    public static String createPerson() {
        String tableDefinition = "id SERIAL PRIMARY KEY," +
                "firstName VARCHAR(255)," +
                "lastName VARCHAR(255)," +
                "email VARCHAR(255) UNIQUE," +
                "phone VARCHAR(255) UNIQUE," +
                "role role DEFAULT 'user'," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP";

        return createTable("person", tableDefinition);
    }

    /**
     * Generates a SQL query to create an 'administrator' table that inherits from 'person'.
     *
     * @return A SQL query string to create the 'administrator' table.
     */
    public static String createAdministrator() {
        String query = drop("TABLE", "administrator") +
                "CREATE TABLE administrator(" +
                "role role DEFAULT 'admin') INHERITS (person)";

        return query;
    }

    /**
     * Generates a SQL query to create a 'transport' type as an ENUM.
     *
     * @return A SQL query string to create the 'transport' type.
     */
    public static String createTransport() {
        String query = drop("TYPE", "transport") +
                "CREATE TYPE transport AS ENUM('BUS', 'TRAIN', 'AIRPLANE')";

        return query;
    }

    /**
     * Generates a SQL query to create a 'partnerStatus' type as an ENUM.
     *
     * @return A SQL query string to create the 'partnerStatus' type.
     */
    public static String createPartnerStatus() {
        String query = drop("TYPE", "partnerStatus") +
                "CREATE TYPE partnerStatus AS ENUM('ACTIVE', 'INACTIVE', 'SUSPENDED')";

        return query;
    }

    /**
     * Generates a SQL query to create a table with the specified name and definition.
     *
     * @param tableName The name of the table to create.
     * @param tableDefinition The column definitions for the table.
     * @return A SQL query string to create the specified table.
     */
    public static String createTable(String tableName, String tableDefinition) {
        return drop("TABLE", tableName) + "CREATE TABLE " + tableName + "(" + tableDefinition + ")";
    }

    /**
     * Generates a SQL query to create a 'partner' table.
     *
     * @return A SQL query string to create the 'partner' table.
     */
    public static String createPartner() {
        String tableDefinition = "id SERIAL PRIMARY KEY," +
                "companyName VARCHAR(255)," +
                "commercialContact VARCHAR(255)," +
                "transportType transport NOT NULL," +
                "geographicZone VARCHAR(255)," +
                "spacialCondition VARCHAR(1000)," +
                "partnerStatus partnerStatus DEFAULT 'ACTIVE' NOT NULL," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP";

        return createTable("partner", tableDefinition);
    }

    public static String createCurrentStatus() {
        String query = drop("TYPE", "CurrentStatus") +
                "CREATE TYPE CurrentStatus AS ENUM('IN PROGRESS', 'ENDED', 'SUSPENDED')";

        return query;
    }

    public static String createContract() {
        String tableDefinition = "id SERIAL PRIMARY KEY," +
                "initDate DATE NOT NULL," +
                "endDate DATE NOT NULL," +
                "specialTarif REAL NOT NULL," +
                "accordConditions VARCHAR(5000)," +
                "renewed BOOL DEFAULT true NOT NULL," +
                "currentStatus currentStatus DEFAULT 'IN PROGRESS'";
        return createTable("contract", tableDefinition);
    }

    public static String createOfferStatus() {
        String query = drop("TYPE", "offerStatus") +
                "CREATE TYPE offerStatus AS ENUM('ACTIVE', 'INACTIVE', 'SUSPENDED')";

        return query;
    }

    public static String createReductionType() {
        String query = drop("TYPE", "reductionType") +
                "CREATE TYPE reductionType AS ENUM('PERCENTAGE', 'FIXED-PRICE')";

        return query;
    }

    public static String createPromotionalOffer() {
        String tableDefinition = "id SERIAL PRIMARY KEY," +
                "name VARCHAR(255) NOT NULL," +
                "description TEXT NOT NULL," +
                "initDate DATE NOT NULL," +
                "endDate DATE NOT NULL," +
                "reductionType reductionType NOT NULL," +
                "reductionValue REAL NOT NULL," +
                "currentStatus OfferStatus DEFAULT 'ACTIVE'";
        return createTable("promotionalOffer", tableDefinition);
    }


    public static String createTicketStatus() {
        String query = drop("TYPE", "TicketStatus") +
                "CREATE TYPE TicketStatus AS ENUM('SOLD', 'CANCELED', 'WAITING')";

        return query;
    }

    public static String createTicket() {
        String tableDefinition = "id SERIAL PRIMARY KEY," +
                "transportType transport NOT NULL," +
                "buyingPrice DECIMAL(40, 15) NOT NULL," +
                "sellingPrice DECIMAL(40, 15) NOT NULL," +
                "sellingDate TIMESTAMP NOT NULL," +
                "ticketStatus TicketStatus DEFAULT 'WAITING' NOT NULL";
        return createTable("ticket", tableDefinition);
    }

}
