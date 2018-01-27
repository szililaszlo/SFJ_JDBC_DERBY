package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {
    final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    final String URL = "jdbc:derby:sampleDB;create=true";
    final String USERNAME = "abc";
    final String PASSWORD = "abc";

    public Db() {
        try {
            Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            System.out.println("Sikeres kapcsolat!");
        } catch (SQLException e) {
            System.out.println("Sikertelen kapcsolat!");
            e.printStackTrace();
        }
    }
}
