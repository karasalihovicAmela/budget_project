package com.javaacademy.repositories;

import com.javaacademy.entities.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Amela Karasalihovic
 */
public class UserRepository implements Repository<User> {

    public void createTable(Connection connection) {
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
                    " LOGGED_IN        BOOLEAN                 NOT NULL)";
            statement.execute(sql);
            statement.close();
        } catch (SQLException e) {
            System.out.println("Table can not be created!");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }

    public User insert(Connection connection, User user) {
        // SQL Statements
        try {
            Statement statement = connection.createStatement();
            // CREATE USER
            String sql = "INSERT INTO user_ba (ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, LOGGED_IN) " +
                    "VALUES (" + user.getId() + ", '" + user.getFirstName() + "', '" + user.getLastName() +
                    "', '" + user.getEmail() + "', '" + user.getPassword() + "', FALSE)";
            statement.execute(sql);
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
                boolean loggedIn = resultSet.getBoolean("LOGGED_IN");
                user = new User(id, firstName, lastName, emailDb, passwordDb, loggedIn);
            }
            System.out.println("User found");
            statement.close();
            return user;
        } catch (SQLException e) {
            System.out.println("User can not be found!");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
            return null;
        }
    }

    public User findById(Connection connection, Integer id) {
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
                boolean loggedIn = resultSet.getBoolean("LOGGED_IN");
                user = new User(idDb, firstName, lastName, emailDb, passwordDb, loggedIn);
            }
            statement.close();
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
                boolean loggedIn = resultSet.getBoolean("LOGGED_IN");
                user = new User(id, firstName, lastName, emailDb, passwordDb, loggedIn);
            }
            statement.close();
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

    public void update(Connection connection, User user) {
        try {
            Statement statement = connection.createStatement();
            String sql = "UPDATE user_ba SET " +
                    "FIRST_NAME = '" + user.getFirstName() + "', " +
                    "LAST_NAME = '" + user.getLastName() + "', " +
                    "EMAIL = '" + user.getEmail() + "', " +
                    "PASSWORD = '" + user.getPassword() + "', " +
                    "LOGGED_IN = " + user.getLoggedIn() + " " +
                    "WHERE id = " + user.getId();
            statement.execute(sql);
            statement.close();
        } catch (SQLException e) {
            System.out.println("User can not be updated!");
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public User getLoggedInUser(Connection connection){
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM user_ba WHERE LOGGED_IN = true";
            ResultSet resultSet = statement.executeQuery(sql);

            User user = null;
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String firstName = resultSet.getString("FIRST_NAME");
                String lastName = resultSet.getString("LAST_NAME");
                String emailDb = resultSet.getString("EMAIL");
                String passwordDb = resultSet.getString("PASSWORD");
                boolean loggedIn = resultSet.getBoolean("LOGGED_IN");
                user = new User(id, firstName, lastName, emailDb, passwordDb, loggedIn);
            }
            statement.close();
            return user;
        } catch (SQLException e) {
            System.out.println("User can not be found!");
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return null;
        }
    }

}
