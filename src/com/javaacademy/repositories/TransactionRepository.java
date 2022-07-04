package com.javaacademy.repositories;

import com.javaacademy.entities.Transaction;
import com.javaacademy.services.CategoryService;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionRepository implements Repository<Transaction> {

    @Override
    public void createTable(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE if not EXISTS transaction (" +
                    "ID             INT    PRIMARY KEY  NOT NULL, " +
                    "USER_ID        INT                 NOT NULL, " +
                    "CATEGORY_ID    INT                 NOT NULL, " +
                    "AMOUNT         DECIMAL             NOT NULL, " +
                    "FOREIGN KEY (USER_ID) REFERENCES user_ba(ID), " +
                    "FOREIGN KEY (CATEGORY_ID) REFERENCES category(ID) " +
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

    @Override
    public Transaction insert(Connection connection, Transaction object) {
        try {
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO transaction (ID, USER_ID, CATEGORY_ID, AMOUNT) " +
                    "VALUES ("
                    + object.getId() + ", "
                    + object.getUserId() + ", "
                    + object.getCategoryId() + ", "
                    + object.getAmount() + ")";
            statement.execute(sql);
            statement.close();

            new CategoryService().updateCategoryBalance(connection, object);

            return object;
        } catch (SQLException e) {
            System.out.println("Data cannot be inserted!");
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return null;
        }
    }

    @Override
    public void update(Connection connection, Transaction object) {

    }

    @Override
    public Transaction findById(Connection connection, Integer id) {
        return null;
    }
}
