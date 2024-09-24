package org.app.Tools;

public class DataFile {
    public static void main(String[] args) {

    }
    public DataFile() {}

    protected static String url() {
        return "jdbc:postgresql://mohammed:5432/ecomove";
    }

    protected static String user() {
        return "postgres";
    }

    protected static String password() {
        return "superman";
    }
}
