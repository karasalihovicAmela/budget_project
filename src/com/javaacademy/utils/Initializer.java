package com.javaacademy.utils;

import com.javaacademy.repositories.CategoryRepository;
import com.javaacademy.repositories.TransactionRepository;
import com.javaacademy.repositories.UserRepository;

import java.sql.Connection;

public class Initializer {

    public void initializeDb(Connection connection){
        UserRepository userRepository = new UserRepository();
        userRepository.createTable(connection);

        CategoryRepository categoryRepository = new CategoryRepository();
        categoryRepository.createTable(connection);

        TransactionRepository transactionRepository = new TransactionRepository();
        transactionRepository.createTable(connection);
    }

}
