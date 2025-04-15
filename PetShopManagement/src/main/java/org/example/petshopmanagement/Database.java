package org.example.petshopmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static Connection connectDB() {
        String url = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12746904";
        String user = "sql12746904";
        String password = "3kPuWVXqzJ";
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
