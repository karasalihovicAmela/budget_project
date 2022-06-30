package com.javaacademy.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Amela Karasalihovic
 */
public class DatabaseService {

    public Connection connectToDatabase() {
        Connection connection = null;
        try {
            //TODO: DELETE PASSWORD BEFORE PUSHING TO GIT
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/budgeting_app", "postgres", "postgres123");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        return connection;
    }

    public void closeDatabaseConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Can't close connection!");
        }
    }
}
