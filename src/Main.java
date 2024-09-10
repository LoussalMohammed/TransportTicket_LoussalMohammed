import org.app.Models.Helpers.PasswordUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Main {
    private static final String URL = "jdbc:postgresql://localhost:5432/ecomove";
    private static final String USER = "postgres";
    private static final String PASSWORD = "superman";

    public static void main(String[] args) {
        String password = "loussalmohammed@2024.com";

        String hashedPassword = PasswordUtil.hashPassword(password);
        System.out.println(hashedPassword);
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
     * Generates a SQL query to create a table with the specified name and definition.
     *
     * @param tableName The name of the table to create.
     * @param tableDefinition The column definitions for the table.
     * @return A SQL query string to create the specified table.
     */
    public static String createTable(String tableName, String tableDefinition) {
        return drop("TABLE", tableName) + "CREATE TABLE " + tableName + " (" + tableDefinition + ");";
    }


    public static String createRole() {
        String query = drop("TYPE", "role") +
                "CREATE TYPE role AS ENUM('admin', 'user');";
        return query;
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
        return createTable("persons", tableDefinition);
    }

    /**
     * Generates a SQL query to create an 'administrator' table that inherits from 'person'.
     *
     * @return A SQL query string to create the 'administrator' table.
     */
    public static String createAdministrator() {
        String query = drop("TABLE", "administrators") +
                "CREATE TABLE administrators (" +
                "role role DEFAULT 'admin'" +
                ") INHERITS (persons);";
        return query;
    }

    /**
     * Generates a SQL query to create a 'transport' type as an ENUM.
     *
     * @return A SQL query string to create the 'transport' type.
     */
    public static String createTransport() {
        String query = drop("TYPE", "transport") +
                "CREATE TYPE transport AS ENUM('BUS', 'TRAIN', 'AIRPLANE');";
        return query;
    }

    /**
     * Generates a SQL query to create a 'partnerStatus' type as an ENUM.
     *
     * @return A SQL query string to create the 'partnerStatus' type.
     */
    public static String createPartnerStatus() {
        String query = drop("TYPE", "partnerStatus") +
                "CREATE TYPE partnerStatus AS ENUM('ACTIVE', 'INACTIVE', 'SUSPENDED');";
        return query;
    }

    /**
     * Generates a SQL query to create a 'partner' table.
     *
     * @return A SQL query string to create the 'partner' table.
     */
    public static String createPartner() {
        String tableDefinition = "id UUID PRIMARY KEY," +
                "companyName VARCHAR(255)," +
                "commercialContact VARCHAR(255)," +
                "transportType transport NOT NULL," +
                "geographicZone VARCHAR(255)," +
                "spacialCondition TEXT," +
                "partnerStatus partnerStatus DEFAULT 'ACTIVE' NOT NULL," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP";
        return createTable("partners", tableDefinition);
    }

    /**
     * Generates a SQL query to create a 'currentStatus' type as an ENUM.
     *
     * @return A SQL query string to create the 'currentStatus' type.
     */
    public static String createCurrentStatus() {
        String query = drop("TYPE", "currentStatus") +
                "CREATE TYPE currentStatus AS ENUM('IN PROGRESS', 'ENDED', 'SUSPENDED');";
        return query;
    }

    /**
     * Generates a SQL query to create a 'contract' table.
     *
     * @return A SQL query string to create the 'contract' table.
     */
    public static String createContract() {
        String tableDefinition = "id UUID PRIMARY KEY," +
                "initDate DATE NOT NULL," +
                "endDate DATE," + // Modified to allow NULL
                "specialTariff DECIMAL(40, 15) NOT NULL," +
                "accordConditions VARCHAR(5000)," +
                "renewed BOOL DEFAULT true NOT NULL," +
                "currentStatus currentStatus DEFAULT 'IN PROGRESS'," +
                "partner_id UUID REFERENCES partners(id) ON UPDATE CASCADE ON DELETE CASCADE"; // Added foreign key
        return createTable("contracts", tableDefinition);
    }

    /**
     * Generates a SQL query to create an 'offerStatus' type as ENUM.
     *
     * @return A SQL query string to create the 'offerStatus' type.
     */
    public static String createOfferStatus() {
        String query = drop("TYPE", "offerStatus") +
                "CREATE TYPE offerStatus AS ENUM('ACTIVE', 'INACTIVE', 'SUSPENDED');";
        return query;
    }

    /**
     * Generates a SQL query to create a 'reductionType' type as ENUM.
     *
     * @return A SQL query string to create the 'reductionType' type.
     */
    public static String createReductionType() {
        String query = drop("TYPE", "reductionType") +
                "CREATE TYPE reductionType AS ENUM('PERCENTAGE', 'FIXED-PRICE');";
        return query;
    }


    /**
     * Generates a SQL query to create a 'promotionalOffer' table.
     *
     * @return A SQL query string to create the 'promotionalOffer' table.
     */
    public static String createPromotionalOffer() {
        String tableDefinition = "id UUID PRIMARY KEY," +
                "name VARCHAR(255) NOT NULL," +
                "description TEXT NOT NULL," +
                "initDate DATE NOT NULL," +
                "endDate DATE NOT NULL," +
                "reductionType reductionType NOT NULL," +
                "reductionValue DECIMAL(40, 15) NOT NULL," +
                "currentStatus offerStatus DEFAULT 'ACTIVE'," +
                "contract_id UUID REFERENCES contracts(id) ON UPDATE CASCADE ON DELETE CASCADE"; // Added foreign key
        return createTable("promotionalOffers", tableDefinition);
    }

    /**
     * Generates a SQL query to create a 'ticketStatus' type as ENUM.
     *
     * @return A SQL query string to create the 'ticketStatus' type.
     */
    public static String createTicketStatus() {
        String query = drop("TYPE", "ticketStatus") +
                "CREATE TYPE ticketStatus AS ENUM('SOLD', 'CANCELED', 'WAITING');";
        return query;
    }

    /**
     * Generates a SQL query to create a 'ticket' table.
     *
     * @return A SQL query string to create the 'ticket' table.
     */
    public static String createTicket() {
        String tableDefinition = "id UUID PRIMARY KEY," +
                "transportType transport NOT NULL," +
                "buyingPrice DECIMAL(40, 15) NOT NULL," +
                "sellingPrice DECIMAL(40, 15) NOT NULL," +
                "sellingDate TIMESTAMP NOT NULL," +
                "ticketStatus ticketStatus DEFAULT 'WAITING' NOT NULL," +
                "contract_id UUID REFERENCES contracts(id) ON UPDATE CASCADE ON DELETE CASCADE," + // Added foreign key
                "promotionalOffer_id UUID REFERENCES promotionalOffers(id) ON UPDATE CASCADE ON DELETE CASCADE"; // Added foreign key
        return createTable("tickets", tableDefinition);
    }


    public static String createContractPromotionalOffer() {
        String tableDefinition = "contract_id UUID REFERENCES contracts(id),"+
                "promotional_offer_id UUID REFERENCES promotionalOffers(id),"+
                "PRIMARY KEY (contract_id, promotional_offer_id)";

        return createTable("contract_promotionalOffers", tableDefinition);
    }


}