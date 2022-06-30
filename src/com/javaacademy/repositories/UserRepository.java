package com.javaacademy.repositories;

import com.javaacademy.entities.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Amela Karasalihovic
 */
public class UserRepository {

    public void createUserTable(Connection connection) {
        // SQL Statements
        try {
            Statement statement = connection.createStatement();
            // CREATE TABLE
            //Postgres has its default table name user, so we need to use some other name user_ba (user budgeting app)
            String sql = "CREATE TABLE if not EXISTS user_ba  " +
                    "(ID               INT     PRIMARY KEY     NOT NULL," +
                    " FIRST_NAME       TEXT                    NOT NULL," +
                    " LAST_NAME        TEXT                    NOT NULL," +
                    " EMAIL            TEXT                    NOT NULL," +
                    " PASSWORD         TEXT                    NOT NULL," +
                    " TOTAL_BALANCE    NUMERIC )";
            statement.execute(sql);
            System.out.println("Successfully created user table in DB");
            statement.close();
        } catch (SQLException e) {
            System.out.println("Table can not be created!");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }

    public User registerUser(Connection connection, User user) {
        // SQL Statements
        try {
            Statement statement = connection.createStatement();
            // CREATE USER
            String sql = "INSERT INTO user_ba (ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, TOTAL_BALANCE) " +
                    "VALUES (" + user.getId() + ", '" + user.getFirstName() + "', '" + user.getLastName() +
                    "', '" + user.getEmail() + "', '" + user.getPassword() + "', " + user.getTotalBalance() + ")";
            statement.execute(sql);
            System.out.println("Successfully created user in DB");
            statement.close();
            return user;
        } catch (SQLException e) {
            System.out.println("User can not be created!");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
            return null;
        }
    }

    public User findUserByEmailAndPassword(Connection connection, String email, String password) {
        try {
            Statement statement = connection.createStatement();

            // Find user by email and password
            String sql = "SELECT * FROM user_ba WHERE EMAIL = '" + email +
                    "' AND PASSWORD = '" + password + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            User user = null;
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String firstName = resultSet.getString("FIRST_NAME");
                String lastName = resultSet.getString("LAST_NAME");
                String emailDb = resultSet.getString("EMAIL");
                String passwordDb = resultSet.getString("PASSWORD");
                double totalBalance = resultSet.getDouble("TOTAL_BALANCE");
                user = new User(id, firstName, lastName, emailDb, passwordDb, null, totalBalance);
            }
            System.out.println("User found");
            return user;
        } catch (SQLException e) {
            System.out.println("User can not be found!");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
            return null;
        }
    }

    public User findUserById(Connection connection, Integer id) {
        try {
            Statement statement = connection.createStatement();

            // Find user by email and password
            String sql = "SELECT * FROM user_ba WHERE ID = " + id;
            ResultSet resultSet = statement.executeQuery(sql);
            User user = null;
            while (resultSet.next()) {
                int idDb = resultSet.getInt("ID");
                String firstName = resultSet.getString("FIRST_NAME");
                String lastName = resultSet.getString("LAST_NAME");
                String emailDb = resultSet.getString("EMAIL");
                String passwordDb = resultSet.getString("PASSWORD");
                double totalBalance = resultSet.getDouble("TOTAL_BALANCE");
                user = new User(idDb, firstName, lastName, emailDb, passwordDb, null, totalBalance);
            }
            if (user != null) {
                System.out.println("User found " + user.getId() + " name: " + user.getLastName());
                return user;
            }
            return null;
        } catch (SQLException e) {
            System.out.println("User can not be found!");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
            return null;
        }
    }

    public User findUserByEmail(Connection connection, String email) {
        try {
            Statement statement = connection.createStatement();

            // Find user by email and password
            String sql = "SELECT * FROM user_ba WHERE EMAIL = '" + email + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            User user = null;
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String firstName = resultSet.getString("FIRST_NAME");
                String lastName = resultSet.getString("LAST_NAME");
                String emailDb = resultSet.getString("EMAIL");
                String passwordDb = resultSet.getString("PASSWORD");
                double totalBalance = resultSet.getDouble("TOTAL_BALANCE");
                user = new User(id, firstName, lastName, emailDb, passwordDb, null, totalBalance);
            }
            if (user != null) {
                System.out.println("User found!");
                return user;
            }
            return null;
        } catch (SQLException e) {
            System.out.println("User can not be found!");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
            return null;
        }
    }
}
