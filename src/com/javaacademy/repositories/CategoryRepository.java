package com.javaacademy.repositories;

import com.javaacademy.entities.Category;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Amela Karasalihovic
 */
public class CategoryRepository implements Repository<Category> {

    public void createTable(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE if not EXISTS category (" +
                    "ID             INT    PRIMARY KEY  NOT NULL, " +
                    "USER_ID        INT                 NOT NULL, " +
                    "NAME           CHAR(255)           NOT NULL    UNIQUE, " +
                    "AMOUNT         DECIMAL             NOT NULL, " +
                    "CURRENT_AMOUNT DECIMAL             NOT NULL, " +
                    "FOREIGN KEY (USER_ID) REFERENCES user_ba(ID) " +
                    ")";
            statement.execute(sql);
            statement.close();
        } catch (SQLException e) {
            System.out.println("Table can not be created!");
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public Category insert(Connection connection, Category category) {
        try {
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO category (ID, USER_ID, NAME, AMOUNT, CURRENT_AMOUNT) " +
                    "VALUES ("
                    + category.getId() + ", "
                    + category.getUserId() + ", "
                    + "'" + category.getName().toLowerCase() + "', "
                    + category.getAmount() + ", "
                    + category.getCurrentAmount() + ")";
            statement.execute(sql);
            statement.close();
            return category;
        } catch (SQLException e) {
            System.out.println("Data cannot be inserted!");
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return null;
        }
    }

    public void update(Connection connection, Category category) {
        try {
            Statement statement = connection.createStatement();
            String sql = "UPDATE category SET " +
                    "ID = " + category.getId() + ", " +
                    "USER_ID = " + category.getUserId() + ", " +
                    "NAME = '" + category.getName().toLowerCase() + "', " +
                    "AMOUNT = " + category.getAmount() + ", " +
                    "CURRENT_AMOUNT = " + category.getCurrentAmount() + " ";
            statement.execute(sql);
            statement.close();
        } catch (SQLException e) {
            System.out.println("Data cannot be inserted!");
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    @Override
    public Category findById(Connection connection, Integer id) {
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM category " +
                    "WHERE ID = " + id + " ";
            ResultSet resultSet = statement.executeQuery(sql);
            Category category = null;
            if (resultSet.next()) {
                category = new Category();
                category.setId(resultSet.getInt("ID"));
                category.setUserId(resultSet.getInt("USER_ID"));
                category.setName(resultSet.getString("NAME"));
                category.setAmount(resultSet.getDouble("AMOUNT"));
                category.setCurrentAmount(resultSet.getDouble("CURRENT_AMOUNT"));
            }
            statement.close();
            return category;
        } catch (SQLException e) {
            System.out.println("Data cannot be found!");
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return null;
        }
    }

    public Category findByName(Connection connection, String name, Integer userId) {
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM category " +
                    "WHERE NAME = '" + name.toLowerCase() + "' " +
                    "AND USER_ID = " + userId + " ";
            ResultSet resultSet = statement.executeQuery(sql);
            Category category = null;
            if (resultSet.next()) {
                category = new Category();

                Integer id = resultSet.getInt("ID");
                category.setId(id);

                category.setUserId(userId);
                category.setName(name);

                Double amount = resultSet.getDouble("AMOUNT");
                category.setAmount(amount);

                Double currentAmount = resultSet.getDouble("CURRENT_AMOUNT");
                category.setCurrentAmount(currentAmount);
            }
            statement.close();
            return category;
        } catch (SQLException e) {
            System.out.println("Data cannot be found!");
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return null;
        }
    }

    public List<Category> findAllByUserId(Connection connection, Integer userId) {
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM category " +
                    "WHERE USER_ID = " + userId + " ";
            ResultSet resultSet = statement.executeQuery(sql);
            List<Category> categories = new ArrayList<>();
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt("ID"));
                category.setUserId(userId);
                category.setName(resultSet.getString("NAME"));
                category.setAmount(resultSet.getDouble("AMOUNT"));
                category.setCurrentAmount(resultSet.getDouble("CURRENT_AMOUNT"));
                categories.add(category);
            }
            statement.close();
            return categories;
        } catch (SQLException e) {
            System.out.println("Data cannot be found!");
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return null;
        }
    }

}

