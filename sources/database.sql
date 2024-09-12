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
            "password VARCHAR(255) NOT NULL"+
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"+
            "deleted_at TIMESTAMP DEFAULT NULL";
return createTable("person", tableDefinition);
}

/**
 * Generates a SQL query to create an 'administrator' table that inherits from 'person'.
 *
 * @return A SQL query string to create the 'administrator' table.
 */
public static String createAdministrator() {
    String query = drop("TABLE", "administrator") +
            "CREATE TABLE administrator (" +
            "role role DEFAULT 'admin'" +
            ") INHERITS (person);";
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
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"+
            "deleted_at TIMESTAMP DEFAULT NULL";
return createTable("partner", tableDefinition);
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
    String tableDefinition = "id SERIAL PRIMARY KEY," +
            "initDate DATE NOT NULL," +
            "endDate DATE," + // Modified to allow NULL
            "specialTarif DECIMAL(40, 15) NOT NULL," +
            "accordConditions VARCHAR(5000)," +
            "renewed BOOL DEFAULT true NOT NULL," +
            "currentStatus currentStatus DEFAULT 'IN PROGRESS'," +
            "partner_id INTEGER REFERENCES partners(id) ON UPDATE CASCADE ON DELETE CASCADE,"+ // Added foreign key
            "deleted_at TIMESTAMP DEFAULT NULL";
    return createTable("contract", tableDefinition);
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
    String tableDefinition = "id SERIAL PRIMARY KEY," +
            "name VARCHAR(255) NOT NULL," +
            "description TEXT NOT NULL," +
            "initDate DATE NOT NULL," +
            "endDate DATE NOT NULL," +
            "reductionType reductionType NOT NULL," +
            "reductionValue DECIMAL(40, 15) NOT NULL," +
            "currentStatus offerStatus DEFAULT 'ACTIVE'," +
            "contract_id INTEGER REFERENCES contracts(id) ON UPDATE CASCADE ON DELETE CASCADE"+
            "deleted_at TIMESTAMP DEFAULT NULL";
    return createTable("promotionalOffer", tableDefinition);
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
    String tableDefinition = "id SERIAL PRIMARY KEY," +
            "transportType transport NOT NULL," +
            "buyingPrice DECIMAL(40, 15) NOT NULL," +
            "sellingPrice DECIMAL(40, 15) NOT NULL," +
            "sellingDate TIMESTAMP NOT NULL," +
            "ticketStatus ticketStatus DEFAULT 'WAITING' NOT NULL," +
            "contract_id INTEGER REFERENCES contracts(id) ON UPDATE CASCADE ON DELETE CASCADE," + // Added foreign key
            "promotionalOffer_id INTEGER REFERENCES promotionalOffers(id) ON UPDATE CASCADE ON DELETE CASCADE,"+ // Added foreign key
            "transporter VARCHAR(255) ,"+
            "deleted_at TIMESTAMP DEFAULT NULL";
    return createTable("ticket", tableDefinition);
}


public static String createContractPromotionalOffer() {
    String tableDefinition = "contract_id UUID REFERENCES contract(id),"+
    "promotional_offer_id UUID REFERENCES promotionaloffer(id),"+
    "PRIMARY KEY (contract_id, promotional_offer_id),"+
    "deleted_at TIMESTAMP DEFAULT NULL";

    return createTable("contract_promotionalOffer", tableDefinition);
}

public static String createRoute() {
    String tableDefinition = "id SERIAL PRIMARY KEY,"+
    "departureCity VARCHAR(255) NOT NULL,"+
    "destinationCity VARCHAR(255) NOT NULL,"+
    "departureDate VARCHAR(255) NOT NULL,"+
    "price DECIMAL(20, 25) NOT NULL,"+
    "ticketId UUID NOT NULL,"+
    "FOREIGN KEY (ticketId) REFERENCES tickets(id) ON CASCADE UPDATE ON CASCADE DELETE,"+
    "partnerId UUID NOT NULL,"+
    "FOREIGN KEY (partnerId) REFERENCES partners(id) ON CASCADE UPDATE ON CASCADE DELETE,"+
    "deleted_at TIMESTAMP DEFAULT NULL";

return createTable("routes", tableDefinition);
}

public static String createReservation() {
    String tableDefinition = "id SERIAL PRIMARY KEY,"+
    "reserved_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"+
    "canceled_at TIMESTAMP DEFAULT NULL,"+
    "clientId INT NOT NULL,"+
    "FOREIGN KEY (clientId) REFERENCES persons(id),"+
    "ticketId UUID NOT NULL,"+
    "FOREIGN KEY (ticketId) REFERENCES tickets(id) ON CASCADE UPDATE ON CASCADE DELETE,"+
    "partnerId UUID NOT NULL,"+
    "FOREIGN KEY (partnerId) REFERENCES partners(id) ON CASCADE UPDATE ON CASCADE DELETE"+
    "deleted_at TIMESTAMP DEFAULT NULL";

return createTable("routes", tableDefinition);
}