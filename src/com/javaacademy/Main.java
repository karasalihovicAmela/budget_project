package com.javaacademy;

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

        int choice = -1;

        while(choice != 0) {
            System.out.println("Welcome to budgeting app! Please choose: \n0 - Close app, " +
                    "\n1 - Log into app or \n2 - Register user ");
            choice = scanner.nextInt();
            switch (choice) {
                case 0:
                    System.out.println("Thanks for visiting us!");
                    break;
                case 1:
                    System.out.println("You choose to log into app! Please follow next steps: ");
                    userService.login(scanner, connection);
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

        databaseService.closeDatabaseConnection(connection);
    }
}
