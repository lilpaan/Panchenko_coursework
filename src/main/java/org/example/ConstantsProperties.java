package org.example;

public class ConstantsProperties {
    public static final String MY_EXAMPLE_CONSTANT = "keytoprop";
    public static final String ID = "id";
    public static final String AUTO_BRAND = "autoBrand";
    public static final String MAKER_COUNTRY = "makerCountry";
    public static final String PATH_TO_CSV = "carInfo.csv";
    public static final String PATH_TO_XML = "carInfo.xml";
    public static final String CAR = "car";
    public static final String CARS = "cars";

    public static final String MONGODB_HOST = "mongodb://localhost:27017";
    public static final String MONGODB_DATABASE_NAME = "dbFor5Lab";
    public static final String MONGODB_COLLECTION_NAME = "collectionFor5Lab";
    public static final String MONGODB_DATE_TEMPLATE = "yyyy/MM/dd";
    public static final String MONGODB_COLLECTION_ALREADY_EXIST = "collection already exist";

    public static final String ACTOR_NAME = "system!";

    public static final String MY_SQL_ADDRESS = "jdbc:mysql://localhost:3306/entities";
    public static final String MY_SQL_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String MY_SQL_USER_NAME = "root";
    public static final String MY_SQL_PASSWORD = "root";
    public static final String MY_SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + "Cars ("
            + "id SERIAL, "
            + "autoBrand TEXT, "
            + "makerCountry TEXT);";
    public static final String MY_SQL_INSERT = "INSERT INTO Cars(id, autoBrand, makerCountry) VALUES ('%s', '%s', '%s');";
    public static final String MY_SQL_GET_ID = "SELECT * FROM Cars WHERE id = %d;";
    public static final String MY_SQL_SELECT_ALL_DATA = "SELECT * FROM Cars;";
    public static final String MY_SQL_DELETE = "DELETE FROM Cars WHERE id = %d;";
    public static final String MY_SQL_UPDATE = "UPDATE Cars SET autoBrand = '%s', makerCountry = '%s' WHERE id = %d;";
    public static final String MY_SQL_DELETE_ALL_DATA = "DELETE FROM Cars";
}
