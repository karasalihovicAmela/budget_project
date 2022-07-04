package com.javaacademy;

import com.javaacademy.entities.User;
import com.javaacademy.services.CategoryService;
import com.javaacademy.services.DatabaseService;
import com.javaacademy.services.TransactionService;
import com.javaacademy.services.UserService;
import com.javaacademy.utils.Initializer;

import java.io.IOException;
import java.sql.Connection;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        startApp();
    }

    public static void startApp() throws IOException {
        //Initializing services
        Scanner scanner = new Scanner(System.in);
        DatabaseService databaseService = new DatabaseService();
        UserService userService = new UserService();
        CategoryService categoryService = new CategoryService();
        TransactionService transactionService = new TransactionService();

        Connection connection = databaseService.connectToDatabase();

        Initializer initializer = new Initializer();
        initializer.initializeDb(connection);

        User loggedInUser = userService.getLoggedInUser(connection);

        boolean closeApp = false;
        while (!closeApp) {
            if (loggedInUser == null) {
                int choice = -1;

                while (choice != 0) {
                    System.out.println("\nWelcome to Budgeting App! " +
                            "\nPlease choose: " +
                            "\n0 - Close app, " +
                            "\n1 - Log into app or " +
                            "\n2 - Register user ");
                    choice = scanner.nextInt();
                    switch (choice) {
                        case 0:
                            System.out.println("Thanks for using our App!");
                            closeApp = true;
                            break;
                        case 1:
                            System.out.println("You choose to log into app! Please follow next steps: ");
                            loggedInUser = userService.login(scanner, connection);
                            choice = 0;
                            break;
                        case 2:
                            System.out.println("You choose to register user! Please follow next setps: ");
                            userService.register(scanner, connection);
                            break;
                        default:
                            System.out.println("Wrong choice, please try again: ");
                            break;
                    }
                }
            } else {
                int choiceInApp = -1;

                while (choiceInApp != 0) {
                    System.out.println("\nWelcome to Budgeting App Dashboard, " + loggedInUser.getFirstName() + "! " +
                            "\nPlease choose: " +
                            "\n0 - Log out, " +
                            "\n1 - Add a new bugdet category, " +
                            "\n2 - Add a new transaction, " +
                            "\n3 - Check budget status, " +
                            "\n4 - Import transactions from file, or" +
                            "\n5 - Export budget report");
                    choiceInApp = scanner.nextInt();
                    switch (choiceInApp) {
                        case 0:
                            System.out.println("Logging you out!");
                            userService.logout(connection);
                            loggedInUser = null;
                            break;
                        case 1:
                            categoryService.addCategory(connection, scanner);
                            break;
                        case 2:
                            transactionService.addTransaction(connection, scanner);

                            System.out.println("(Press any key to continue...)");
                            System.in.read();
                            break;
                        case 3:
                            categoryService.checkBudgetStatus(connection);

                            System.out.println("(Press any key to continue...)");
                            System.in.read();
                            break;
                        case 4:
                            transactionService.importFromFile(connection, scanner);

                            System.out.println("(Press any key to continue...)");
                            System.in.read();
                            break;
                        case 5:
                            transactionService.exportToFile(connection);

                            System.out.println("(Press any key to continue...)");
                            System.in.read();
                            break;
                        default:
                            System.out.println("Wrong choice, please try again: ");
                            break;
                    }
                }
            }
        }

        databaseService.closeDatabaseConnection(connection);
    }
}
