package com.javaacademy;

import com.javaacademy.entities.User;
import com.javaacademy.repositories.UserRepository;
import com.javaacademy.services.CategoryService;
import com.javaacademy.services.DatabaseService;
import com.javaacademy.services.UserService;

import java.sql.Connection;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	    startApp();
    }

    public static void startApp() {
        //Initializing services
        Scanner scanner = new Scanner(System.in);
        DatabaseService databaseService = new DatabaseService();
        UserService userService = new UserService();
        CategoryService categoryService = new CategoryService();

        Connection connection = databaseService.connectToDatabase();
        UserRepository userRepository = new UserRepository();
        userRepository.createUserTable(connection);

        User loggedInUser = userRepository.getLoggedInUser(connection);
        while (loggedInUser == null) {
            int choice = -1;

            while (choice != 0) {
                System.out.println("\nWelcome to budgeting app! Please choose: " +
                        "\n0 - Close app, " +
                        "\n1 - Log into app or " +
                        "\n2 - Register user ");
                choice = scanner.nextInt();
                switch (choice) {
                    case 0:
                        System.out.println("Thanks for visiting us!");
                        System.exit(0);
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

            while (loggedInUser != null) {
                int choiceInApp = -1;

                while (choiceInApp != 0) {
                    System.out.println("\nWelcome to Budgeting App Dashboard! " +
                            "\nPlease choose: " +
                            "\n0 - Log out, " +
                            "\n1 - Add a new bugdet, " +
                            "\n2 - Add a new transaction, " +
                            "\n3 - Import transactions from file, " +
                            "\n4 - Export budget report or" +
                            "\n5 - Check budget status");
                    choiceInApp = scanner.nextInt();
                    switch (choiceInApp) {
                        case 0:
                            System.out.println("Logging you out!");
                            userService.logout(connection);
                            loggedInUser = null;
                            break;
                        case 1:
                            System.out.println("Not implemented yet!");
                            break;
                        case 2:
                            System.out.println("Not implemented yet!");
                            break;
                        case 3:
                            System.out.println("Not implemented yet!");
                            break;
                        case 4:
                            System.out.println("Not implemented yet!");
                            break;
                        case 5:
                            System.out.println("Not implemented yet!");
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
