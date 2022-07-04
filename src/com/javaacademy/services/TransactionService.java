package com.javaacademy.services;

import com.javaacademy.entities.Category;
import com.javaacademy.entities.Transaction;
import com.javaacademy.entities.User;
import com.javaacademy.repositories.CategoryRepository;
import com.javaacademy.repositories.TransactionRepository;
import com.javaacademy.repositories.UserRepository;
import com.javaacademy.utils.IdGenerator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TransactionService {

    public Transaction addTransaction(Connection connection, Scanner scanner) {
        TransactionRepository transactionRepository = new TransactionRepository();
        Transaction transaction = new Transaction();

        transaction.setId(IdGenerator.getIdAndCheckIfItExists(connection, transactionRepository));

        Category category = null;
        do {
            System.out.println("Transaction category name:");
            scanner = new Scanner(System.in);
            String categoryName = scanner.nextLine();

            if (categoryName.equalsIgnoreCase(":q")) {
                return null;
            }

            category = new CategoryService().findByName(connection, categoryName);

            if (category == null) {
                System.out.println("Could not find a category with that name. Try again or type \":Q\" for exit.");
            }
        } while (category == null);
        transaction.setCategoryId(category.getId());

        System.out.println("Transaction amount:");
        Double amount = scanner.nextDouble();
        transaction.setAmount(amount);

        User user = new UserService().getLoggedInUser(connection);
        transaction.setUserId(user.getId());

        transactionRepository.insert(connection, transaction);

        return transaction;
    }

    public void importFromFile(Connection connection, Scanner scanner) {
        System.out.println("Specify the name of the file with transactions (with extension):");
        scanner = new Scanner(System.in);
        String filename = scanner.nextLine();
        try {
            FileReader fileReader = new FileReader(filename);

            int counter = 0;
            int failed = 0;
            int c;
            StringBuilder sb = new StringBuilder();
            while ((c = fileReader.read()) != -1) {
                sb.append((char) c);
                if (String.valueOf((char) c).equals("\n")) {
                    String transactionLine = sb.toString();
                    sb = new StringBuilder();
                    String[] data = transactionLine.split(";");

                    User user = new UserService().getLoggedInUser(connection);

                    TransactionRepository transactionRepository = new TransactionRepository();
                    Transaction transaction = new Transaction();
                    transaction.setId(IdGenerator.getIdAndCheckIfItExists(connection, transactionRepository));
                    transaction.setUserId(user.getId());
                    Category category = new CategoryService().findByName(connection, data[0]);
                    if (category == null) {
                        System.out.println((counter + 1) + ": Could not find category with name \"" + data[0] + "\"!");
                        counter++;
                        failed++;
                        continue;
                    }
                    transaction.setCategoryId(category.getId());
                    transaction.setAmount(Double.valueOf(data[1]));

                    System.out.println((counter + 1) + ": Importing transaction: " + data[0] + " - $" + data[1].replace("\n", ""));
                    Transaction transaction1 = transactionRepository.insert(connection, transaction);
                    if (transaction1 == null) {
                        failed++;
                    }

                    counter++;
                }
            }
            System.out.println("- Finished importing " + counter + " transaction rows from " + filename + "! (Imported " + (counter - failed) + "/" + counter + " transactions!)");
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not find a file with that name!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Something is wrong with the file data!");
        }

    }

    public void exportToFile(Connection connection) {
        User user = new UserRepository().getLoggedInUser(connection);
        List<Category> categoryList = new CategoryRepository().findAllByUserId(connection, user.getId());

        String timestamp = Calendar.getInstance().toInstant().toString();

        try {
            FileWriter fw = new FileWriter("Export - " + user.getFirstName() + " " + user.getLastName() + " - " + timestamp.replace(":","") + ".txt");

            for (Category category : categoryList) {
                fw.append(" - " + category.getName().trim() + " " +
                        "- $" + new BigDecimal(category.getCurrentAmount()).setScale(2, RoundingMode.HALF_EVEN) + "/$" + category.getAmount() + " " +
                        "- " + new BigDecimal((category.getCurrentAmount() / category.getAmount()) * 100).setScale(2, RoundingMode.HALF_EVEN) + "%\n");
            }
            fw.close();

        } catch (IOException e) {
            System.out.println("Unable to export budget status, error: " + e.getMessage());
        }

    }
}
