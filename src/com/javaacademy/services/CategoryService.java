package com.javaacademy.services;

import com.javaacademy.entities.Category;
import com.javaacademy.entities.Transaction;
import com.javaacademy.entities.User;
import com.javaacademy.repositories.CategoryRepository;
import com.javaacademy.repositories.UserRepository;
import com.javaacademy.utils.IdGenerator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

/**
 * @author Amela Karasalihovic
 */
public class CategoryService {

    public void addCategory(Connection connection, Scanner scanner) {
        CategoryRepository categoryRepository = new CategoryRepository();
        Category category = new Category();

        category.setId(IdGenerator.getIdAndCheckIfItExists(connection, categoryRepository));

        System.out.println("Budget category name:");
        scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        category.setName(name);

        System.out.println("Budget category amount:");
        Double amount = scanner.nextDouble();
        category.setAmount(amount);
        category.setCurrentAmount(0.0d);

        UserRepository userRepository = new UserRepository();
        User user = userRepository.getLoggedInUser(connection);
        category.setUserId(user.getId());

        categoryRepository.insert(connection, category);
    }

    public Category findByName(Connection connection, String name) {
        UserRepository userRepository = new UserRepository();
        User currentUser = userRepository.getLoggedInUser(connection);

        CategoryRepository categoryRepository = new CategoryRepository();
        return categoryRepository.findByName(connection, name, currentUser.getId());
    }

    public void checkBudgetStatus(Connection connection) {
        User user = new UserService().getLoggedInUser(connection);
        List<Category> categories = new CategoryRepository().findAllByUserId(connection, user.getId());
        System.out.println("Budget status:");
        for (Category category : categories) {
            System.out.println(" - " + category.getName().trim() + " " +
                    "- $" + new BigDecimal(category.getCurrentAmount()).setScale(2, RoundingMode.HALF_EVEN) + "/$" + category.getAmount() + " " +
                    "- " + new BigDecimal((category.getCurrentAmount() / category.getAmount()) * 100).setScale(2, RoundingMode.HALF_EVEN) + "%");
        }
    }

    public void updateCategoryBalance(Connection connection, Transaction transaction) {
        CategoryRepository categoryRepository = new CategoryRepository();
        Category category = categoryRepository.findById(connection, transaction.getCategoryId());
        category.setCurrentAmount(category.getCurrentAmount() + transaction.getAmount());
        if (category.getCurrentAmount() >= category.getAmount()) {
            System.out.println("WARNING: You have reached your limit for the category " + category.getName().trim() + " ($" + new BigDecimal(category.getCurrentAmount()).setScale(2, RoundingMode.HALF_EVEN) + "/$" + category.getAmount() + ")!");
        }
        categoryRepository.update(connection, category);
    }
}
